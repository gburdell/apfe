/*
 * The MIT License
 *
 * Copyright 2015 gburdell.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package apfe.v2.vlogpp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import static apfe.v2.vlogpp.FileCharReader.NL;
import java.util.regex.Matcher;

/**
 * SystemVerilog source file.
 *
 * @author gburdell
 */
public class SourceFile {

    public SourceFile(final String fname, final PrintStream os) throws FileNotFoundException {
        m_os = os;
        push(fname, 0);
    }

    public boolean parse() throws ParseError {
        boolean ok = true;
        char c;
        ParseError error = null;
        while (null != m_is) {
            while (!isEOF()) {
                try {
                    if (acceptOnMatch("\"")) {
                        stringLiteral();
                    } else if (acceptOnMatch("/*")) {
                        blockComment();
                    } else if (acceptOnMatch("//")) {
                        lineComment();
                    } else {
                        final String str = m_is.remainder();
                        Matcher matcher = TicDefine.stPatt1.matcher(str);
                        //NOTE: matches() pattern is anchored w/ implied ^...$
                        if (matcher.matches()) {
                            final TicDefine ticDefine = TicDefine.parse(this, TicDefine.stPatt2.matcher(str));
                            //TODO: ticDefine
                        } else {
                            matcher = TicConditional.stPatt1.matcher(str);
                            if (matcher.matches()) {
                                final TicConditional ticConditional = TicConditional.parse(this, TicConditional.stPatt2.matcher(str));
                                //TODO: ticConditional
                            } else {
                                next();
                            }
                        }
                    }
                } catch (final ParseError pe) {
                    error = pe;
                    break;
                }
            }
            try {
                m_is.close();
            } catch (IOException ex) {
                Logger.getLogger(SourceFile.class.getName()).log(Level.SEVERE, null, ex);
                error = new ParseError("VPP-EXIT");
            }
            if (null != error) {
                throw error;
            }
            m_is = m_stack.empty() ? null : m_stack.pop();
        }
        return ok;
    }

    void accept(int n
    ) {
        m_is.accept(n);
    }

    int la(int n
    ) {
        return m_is.la(n);
    }

    int la() {
        return la(0);
    }

    boolean skipWhiteSpace() {
        while (0 <= " \t".indexOf(la())) {
            next();
        }
        return isEOF();
    }

    public boolean isEOF() {
        return m_is.isEOF();
    }

    public boolean isEOL() {
        return m_is.isEOL();
    }

    private void blockComment() throws ParseError {
        while (!isEOF()) {
            if (acceptOnMatch("*/")) {
                return;
            } else if (acceptOnMatch("/*")) {
                throw new ParseError("VPP-CMNT-2", m_is);
            }
            next();
        }
        if (isEOF()) {
            throw new ParseError("VPP-EOF-1", m_is, "<block-comment>");
        }
    }

    private void lineComment() throws ParseError {
        //slurp entire line
        final String rem = m_is.remainder();
        m_is.accept(rem.length());
        print(rem);
    }

    int[] getStartMark() {
        return new int[]{m_is.getLineNum(), m_is.getColNum()};
    }

    /**
     * IEEE Std 1800-2012 5.9 String literals A string literal is a sequence of
     * characters enclosed by double quotes (""). Nonprinting and other special
     * characters are preceded with a backslash. A string literal shall be
     * contained in a single line unless the new line is immediately preceded by
     * a \ (backslash). In this case, the backslash and the newline are ignored.
     * There is no predefined limit to the length of a string literal.
     */
    void stringLiteral() throws ParseError {
        char c;
        final int started[] = getStartMark();
        boolean isUnterminated = false;
        while (!isUnterminated && !isEOF()) {
            if (acceptOnMatch("\\\n")) {
                //do nothing                
            } else {
                c = (char) la();
                switch (c) {
                    case '\\':  //escaped char
                        print(m_is.substring(2));
                        m_is.accept(2);
                        break;
                    case '"':
                        next();
                        return;
                    case NL:
                        isUnterminated = true;
                        break;
                    default:
                        next();
                }
            }
        }
        if (isUnterminated || isEOF()) {
            //VPP-STRING %s: unterminated string (started at %d:%d (line:col))
            throw new ParseError("VPP-STRING", m_is, started[0], started[1]);
        }
    }

    private boolean acceptOnMatch(final String s) {
        final boolean match = m_is.acceptOnMatch(s);
        if (match) {
            print(s);
        }
        return match;
    }

    int next() {
        final int c = (char) m_is.next();
        print((char) c);
        return c;
    }

    private void push(final String fname, final int lvl) throws FileNotFoundException {
        m_is = new FileCharReader(fname);
        if (0 <= lvl) {
            assert 2 >= lvl;
            //include file would be on non-empty stack
            m_os.printf("`line %d \"%s\" %d\n", m_is.getLineNum(), m_is.getFile().getCanonicalPath(), lvl);
        }
        m_stack.push(m_is);
    }

    private void print(final char c) {
        if (m_echoOn) {
            m_os.print(c);
        }
    }

    private void print(final String s) {
        if (m_echoOn) {
            m_os.print(s);
        }
    }

    private static enum EState {

        eStart,
        eDone
    };

    boolean setEchoOn(final boolean val) {
        final boolean was = m_echoOn;
        m_echoOn = val;
        return was;
    }

    String getLocation() {
        return m_is.getLocation();
    }
    
    FileLocation getFileLocation() {
        return new FileLocation(m_is.getFile(), m_is.getLineNum(), m_is.getColNum());
    }

    private final Stack<FileCharReader> m_stack = new Stack<>();
    private final PrintStream m_os;
    private FileCharReader m_is;
    private boolean m_echoOn = true;
}
