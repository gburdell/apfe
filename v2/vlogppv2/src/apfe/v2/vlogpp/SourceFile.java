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
            while (!m_is.isEOF()) {
                try {
                    if (acceptOnMatch("/*")) {
                        blockComment();
                        //TODO: more if...
                    } else {
                        next();
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

    private void blockComment() throws ParseError {
        while (!m_is.isEOF()) {
            if (acceptOnMatch("*/")) {
                return;
            }
            next();
        }
        if (m_is.isEOF()) {
            throw new ParseError("VPP-EOF-1", m_is, "<block-comment>");
        }
    }

    private boolean acceptOnMatch(final String s) {
        final boolean match = m_is.acceptOnMatch(s);
        if (match && m_echoOn) {
            m_os.print(s);
        }
        return match;
    }

    private void next() {
        final char c = (char) m_is.next();
        if (m_echoOn) {
            m_os.print(c);
        }
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

    private static enum EState {

        eStart,
        eDone
    };

    private final Stack<FileCharReader> m_stack = new Stack<>();
    private final PrintStream m_os;
    private FileCharReader m_is;
    private EState m_state = EState.eStart;
    private boolean m_echoOn = true;
}
