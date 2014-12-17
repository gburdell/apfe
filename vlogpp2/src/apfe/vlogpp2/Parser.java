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
                switch (m_state) {
                    case eStart:
                        if (!lineComment()) {
                            if (!blockComment()) {

                            }
                        }
                    default:
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

    private boolean blockComment() throws ParseException {
        final boolean ok = match("/*");
        if (ok) {
            final MarkerImpl begin = getMark();
            char c;
            while (true) {
                c = accept();
                if (CharBuffer.EOF == c) {
                    throw new ParseException("VPP-CMNT-1", begin);
                }
                if (CharBuffer.NL == c) {
                    c = stNL;
                } else if (match("*/")) {
                    print("*/");
                    break;
                }
                print(c);
            }
            flush();
        }
        return ok;
    }

    private boolean string() {
        final boolean ok = la() == '"';
        if (ok) {
            final MarkerImpl mark = getMark();
            char c = accept();
            while (true) {
                c = accept();
                if ('\\' == c) {
                    //what are we escaping?
                    c = accept();
                }
            }
        }
        return ok;
    }

    private boolean lineComment() {
        final boolean ok = match("//");
        if (ok) {
            char c;
            while (true) {
                c = accept();
                if (CharBuffer.EOF == c) {
                    break;
                }
                if (CharBuffer.NL == c) {
                    c = stNL;
                }
                print(c);
            }
            flush();
        }
        return ok;
    }

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
    
    private void print(final char c) {
        //TODO: printing may be blocked if in conditional
        m_os.print(c);
    }

    private void print(final String s) {
        //TODO: printing may be blocked if in conditional
        m_os.print(s);
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

    private static enum EState {

        eStart, eBlockComment
    }

    private void reset() {
        m_state = EState.eStart;
    }

    private static final char stNL = '\n';

    private EState m_state;
    private final CharBuffer m_buf;
    private final PrintWriter m_os;
}
