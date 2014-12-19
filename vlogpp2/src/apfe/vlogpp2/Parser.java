/*
 * The MIT License
 *
 * Copyright 2014 gburdell.
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
package apfe.vlogpp2;

import apfe.runtime.CharBuffer;
import apfe.runtime.CharBuffer.MarkerImpl;
import apfe.runtime.Marker;
import gblib.Util;
import java.io.PrintWriter;
import java.util.Stack;

/**
 * Parser object associated with CharBuffer.
 *
 * @author gburdell
 */
public class Parser {

    public Parser(final CharBuffer cbuf, final PrintWriter os) {
        m_buf = cbuf;
        m_os = os;
        reset();
    }

    /**
     * Parse contents of CharBuffer.
     *
     * @return true on success, false if error encountered.
     */
    public boolean parse() {
        boolean ok = true;
        char c;
        try {
            while (true) {
                c = la();
                if (CharBuffer.EOF == c) {
                    break;  //while
                }
                if (!string()) {
                    if (!lineComment()) {
                        if (!blockComment()) {
                            if ('`' == la()) {
                                ticDirective();
                            }
                        }
                    }
                }
            }
        } catch (ParseException ex) {
            ok = false;
            ex.printErrorMsg();
        } finally {
            //remember, finally blocks always execute
            flush();
        }
        //TODO: Get here via EOF, ????
        return ok;
    }

    private class ParseException extends Throwable {

        /**
         * Parser exception.
         *
         * @param msg message code.
         * @param args args[0] is MarkerImpl of location.
         */
        private ParseException(final String msg, Object... args) {
            m_msg = msg;
            m_args = args;
        }

        private ParseException(final String msg) {
            this(msg, getMark());
        }

        private void printErrorMsg() {
            MarkerImpl mark = Util.downCast(m_args[0]);
            final String loc = gblib.File.getCanonicalName(mark.getFileName())
                    + ":" + mark.toString();
            m_args[0] = loc;
            Helper.error(m_msg, m_args);
        }

        private final String m_msg;
        private final Object[] m_args;
    }

    private void error(final String msg, final MarkerImpl mark) {
        final String loc = gblib.File.getCanonicalName(mark.getFileName()) + ":" + mark.toString();
        Helper.error(msg, loc);
    }

    private char expect(final String oneOf) throws ParseException {
        final char c = la();
        if (0 > oneOf.indexOf(c)) {
            throw new ParseException("VPP-ERR-1", getMark(), oneOf, c);
        }
        return accept();
    }

    private void ticDirective() throws ParseException {
        //dont echo out anything while processing the directives.
        m_noPrint = true;
        if (!ticInclude()) {

        }
        m_noPrint = false;
    }

    private boolean ticInclude() throws ParseException {
        final boolean ok = match("`include");
        if (ok) {
            spacing();
            final MarkerImpl begin = getMark();
            char ends[] = {expect("\"<"), '\0'};
            StringBuilder path = new StringBuilder();
            char c;
            while (true) {
                c = accept();
                if (('"' != c) && ('>' != c)) {
                    if (CharBuffer.NL == c) {
                        throw new ParseException("VPP-NL-1");
                    }
                    path.append(c);
                } else if (CharBuffer.EOF != c) {
                    ends[1] = c;
                    break;
                } else {
                    throw new ParseException("VPP-EOF-2", begin);
                }
            }
            if (ends[0] != ends[1]) {
                throw new ParseException("VPP-FNAME-1", begin, ends[0], ends[1]);
            }
            spacing(true); //rest of line
            final String inclFname = path.toString().trim();
            if (inclFname.isEmpty()) {
                throw new ParseException("VPP-FNAME-2", begin);
            }
            //TODO look for file.
        }
        return ok;
    }

    private boolean blockComment() throws ParseException {
        final boolean ok = match("/*");
        if (ok) {
            final MarkerImpl begin = getMark();
            char c;
            print("/*");
            while (true) {
                c = accept();
                if (CharBuffer.EOF == c) {
                    throw new ParseException("VPP-CMNT-1", begin);
                }
                if (match("*/")) {
                    print("*/");
                    break;  //while
                }
                print(c);
            }
            flush();
        }
        return ok;
    }

    private boolean string() throws ParseException {
        final boolean ok = la() == '"';
        char la;
        if (ok) {
            final MarkerImpl begin = getMark();
            char c = accept();
            print(c);
            while (true) {
                c = accept();
                if ('\\' == c) {
                    la = accept();
                    if (CharBuffer.EOF == la) {
                        throw new ParseException("VPP-EOF-1", begin);
                    }
                    print(c).print(la);
                } else if (CharBuffer.EOF != c) {
                    print(c);
                    if ('"' == c) {
                        break; //while
                    }
                } else {
                    throw new ParseException("VPP-EOF-1", begin);
                }
            }
        }
        return ok;
    }

    private boolean lineComment() {
        final boolean ok = match("//");
        if (ok) {
            print("//");
            char c;
            while (true) {
                c = accept();
                if (CharBuffer.EOF == c) {
                    break;
                }
                print(c);
                if (CharBuffer.NL == c) {
                    break;  //while
                }
            }
            flush();
        }
        return ok;
    }

    /**
     * Process ([ \t\n]|comment)*
     * @param untilNl if true, slurp until 1st newline; else keep going until
     * no more spacing.
     */
    private void spacing(final boolean untilNl) throws ParseException {
        boolean stay = true;
        char c;
        while (stay) {
            c = la();
            switch (c) {
                case '\n':
                    stay = !untilNl;    //done on 1st newline detect if untilNl
                    //fall through
                case '\t':
                case ' ':
                    print(accept());
                    break;
                default:
                    if (!lineComment()) {
                        if (!blockComment()) {
                            stay = false;
                        }
                    }
            }
        }
    }
    
    private void spacing() throws ParseException {
        spacing(false);
    }

    /**
     * Attempt to match string.
     * <B>No characters are printed here.</B>
     *
     * @param to string to match.
     * @param doAccept accept characters from buffer if true; else characters
     * are left in buffer (and current position does not advance).
     * @return true if exact match.
     */
    private boolean match(final String to, final boolean doAccept) {
        for (int i = 0; i < to.length(); i++) {
            if (la(i) != to.charAt(i)) {
                return false;
            }
        }
        if (doAccept) {
            accept(to.length());
        }
        return true;
    }

    private void flush() {
        m_os.flush();
    }

    private Parser print(final char c) {
        //TODO: printing may be blocked if in conditional
        if (!m_noPrint) {
            m_os.print(c);
        }
        return this;
    }

    private Parser print(final String s) {
        //TODO: printing may be blocked if in conditional
        if (!m_noPrint) {
            m_os.print(s);
        }
        return this;
    }

    private boolean match(final String to) {
        return match(to, true);
    }

    private MarkerImpl getMark() {
        return Util.downCast(m_buf.mark());
    }

    private char accept() {
        return m_buf.accept();
    }

    private char accept(final int n) {
        return m_buf.accept(n);
    }

    private char la() {
        return m_buf.la();
    }

    private char la(final int n) {
        return m_buf.la(n);
    }

    private void reset() {
        ;
    }

    /**
     * Make 'buf' current buffer. If we have an existing buffer, it is pushed
     * onto stack.
     *
     * @param buf new current buffer.
     * @return current buffer.
     */
    private CharBuffer push(final CharBuffer buf) {
        if (null != m_buf) {
            m_bufStk.push(m_buf);
        }
        m_buf = buf;
        return m_buf;
    }

    private final Stack<CharBuffer> m_bufStk = new Stack<>();
    private CharBuffer m_buf;
    private final PrintWriter m_os;
    //Disable printing during "in-between" processing.
    private boolean m_noPrint;
}