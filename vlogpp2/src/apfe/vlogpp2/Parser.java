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

import apfe.runtime.CharBufState;
import apfe.runtime.CharBuffer;
import apfe.runtime.CharBuffer.MarkerImpl;
import apfe.runtime.InputStream;
import apfe.runtime.ParseError;
import gblib.Util;
import gblib.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Stack;

/**
 * Parser object associated with CharBuffer.
 *
 * @author gburdell
 */
public class Parser {

    public static void main(final String argv[]) {
        for (String f : argv) {
            parse(f);
        }
    }

    public static boolean parse(final String fname) {
        return parse(fname, new PrintWriter(System.out, true));
    }

    public static boolean parse(final String fname, final PrintWriter... oses) {
        boolean ok = false;
        try {
            final CharBuffer cbuf = InputStream.create(fname);
            Helper.getTheOne().setFname(fname);
            ok = parse(cbuf, oses);
            Helper.getTheOne().setFname(null);
        } catch (Exception ex) {
            Util.abnormalExit(ex);
        }
        return ok;
    }

    public static boolean parse(final CharBuffer cbuf, final PrintWriter... oses) {
        final Parser parser = new Parser(cbuf, oses);
        final boolean ok = parser.parse();
        parser.done(cbuf.getFileName());
        return ok;
    }

    public Parser(final CharBuffer cbuf, final PrintWriter... oses) {
        m_buf = cbuf;
        m_oses = oses;
        init(cbuf.getFileName());
    }

    private void init(final String fname) {
        String fn = File.getCanonicalPath(fname);
        print("`line 1 \"").print(fn).print("\" 1").print(CharBuffer.NL);
    }

    private void done(final String fname) {
        String fn = File.getCanonicalPath(fname);
        print("`line 0 \"").print(fn).print("\" 2").print(CharBuffer.NL);
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
            boolean resetBuffer;
            while (true) {
                if (hadError()) {
                    ok = false;
                    break;
                }
                c = la();
                resetBuffer = (CharBuffer.EOF == c);
                if (resetBuffer) {
                    if (!pop()) {
                        break;  //while
                    }
                }
                if (!resetBuffer) {
                    if (!string()) {
                        if (!lineComment()) {
                            if (!blockComment()) {
                                if ('`' == c) {
                                    ticDirective();
                                } else {
                                    c = accept();
                                    print(c);
                                }
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
        if (ok) {
            //one last check before we leave
            Util.assertTrue(m_bufStk.isEmpty(), "Expected empty include file stack.");
        }
        return ok;
    }

    private static boolean hadError() {
        return (0 < gblib.MessageMgr.getErrorCnt());
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

        private ParseException(final String msg, MarkerImpl mark, Object[] args) {
            m_msg = msg;
            m_args = new Object[args.length + 1];
            m_args[0] = mark;
            System.arraycopy(args, 0, m_args, 1, args.length);
        }

        private ParseException(final String msg) {
            this(msg, getMark());
        }

        private void printErrorMsg() {
            if (null != m_args[0]) {
                if (m_args[0] instanceof MarkerImpl) {
                    MarkerImpl mark = Util.downCast(m_args[0]);
                    final String loc = gblib.File.getCanonicalPath(mark.getFileName())
                            + ":" + mark.toString();
                    m_args[0] = loc;
                }
            } else {
                //skip the [0]==null
                m_args = Arrays.copyOfRange(m_args, 1, m_args.length);
            }
            Helper.error(m_msg, m_args);
        }

        private final String m_msg;
        private Object[] m_args;
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
            //setup apfe parsing: connect buffer to apfe's singleton state.
            CharBufState.create(m_buf, true);
            AcceptorWithLocation acc = null;
            final MarkerImpl mark = getMark();
            if (match("`define", false)) {
                acc = new TicDefine(mark);
            } else if (matches(stConds)) {
                acc = new TicConditional(mark);
            } else if (matches(stReserved) || isProtectedKeyword()) {
                acc = new TicReserved(mark);
            } else {
                acc = new TicMacroUsage(mark);
            }
            assert (null != acc);
            final boolean hasError = !acc.acceptTrue();
            if (hasError) {
                if (acc.hasError()) {
                    if (acc.hasParseError()) {
                        throw new ParseException(ParseError.stErrCode, null, ParseError.getMessageArgs());
                    } else {
                        throw new ParseException(acc.getMsgCode(), mark, acc.getMsgArgs());
                    }
                } else {
                    //accumulate non-whitespace
                    throw new ParseException("VPP-UNEXPECT-1", mark, getAllNonWhiteSpace());
                }
            } else {
                //we've updated the buf during our tic processing, so dump the
                //contents now
                m_noPrint = false;
                print(m_buf.substring(mark));
            }
        }
        m_noPrint = false;
    }

    private String getAllNonWhiteSpace() {
        StringBuilder sb = new StringBuilder();
        char c;
        while (true) {
            c = la();
            if (Character.isWhitespace(c)) {
                break;
            }
            sb.append(accept());
        }
        return sb.toString();
    }

    private static final String stConds[] = new String[]{
        "`ifdef", "`ifndef", "`else", "`elsif", "`endif"
    };

    private static final String stReserved[] = new String[]{
        //NOTE: these are in order of most likely occurance
        "`timescale",
        "`undef",
        "`default_nettype",
        "`__FILE__",
        "`resetall",
        "`undefineall",
        "`end_keywords",
        "`line",
        "`celldefine",
        "`endcelldefine",
        "`pragma",
        "`__LINE__",
        "`begin_keywords"
    };

    private boolean isProtectedKeyword() {
        boolean isKwrd = false;
        for (String k : stProtected) {
            if (matches(k) && !Helper.getTheOne().isDefined(k.substring(1))) {
                isKwrd = true;
                break;
            }
        }
        return isKwrd;
    }
    
    private static final String stProtected[] = new String[] {"`protected", "`protect"};
    
    private boolean matches(final String... oneOf) {
        for (String s : oneOf) {
            if (match(s, false)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Attempt to match and process `include. I just happened to flush/detail
     * this implementation before switching over to apfe/vlogpp ones, so we're a
     * bit inconsistent in ways tic-directives processed. There are better error
     * detects here (than in apfe/vlogpp one, so another reason to stick w/ this
     * one.
     *
     * @return true if matched.
     * @throws apfe.vlogpp2.Parser.ParseException
     */
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
            //dont process further if conditional blocks us
            if (condTrue()) {
                final Location loc = Location.create(begin);
                File incl = Helper.getTheOne().getInclFile(loc, inclFname);
                if (null == incl) {
                    throw new ParseException("VPP-INCL-4", begin, inclFname);
                }
                //start new buffer
                push(incl);
            }
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
                if (match("*/")) {
                    print("*/");
                    break;  //while
                }
                c = accept();
                if (CharBuffer.EOF == c) {
                    throw new ParseException("VPP-CMNT-1", begin);
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
     * Process ([ \t\n]|comment)
     *
     *
     * @param untilNl if true, slurp until 1st newline; else keep going until no
     * more spacing.
     */
    private void spacing(final boolean untilNl) throws ParseException {
        boolean stay = true;
        char c;
        while (stay) {
            c = la();
            switch (c) {
                case CharBuffer.NL:
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
        for (PrintWriter os : m_oses) {
            os.flush();
        }
    }

    private Parser print(final char c) {
        if (!m_noPrint && condTrue()) {
            for (PrintWriter os : m_oses) {
                os.print(c);
            }
        }
        return this;
    }

    private Parser print(final String s) {
        if (!m_noPrint && condTrue()) {
            for (PrintWriter os : m_oses) {
                os.print(s);
            }
        }
        return this;
    }

    private Parser print(final int v) {
        if (!m_noPrint && condTrue()) {
            for (PrintWriter os : m_oses) {
                os.print(v);
            }
        }
        return this;
    }

    private boolean match(final String to) {
        return match(to, true);
    }

    /*package*/ static Location getLocation(final MarkerImpl mark) {
        return Location.create(mark);
    }

    private Location getLocation() {
        return getLocation(getMark());
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

    private void push(final File fn) throws ParseException {
        assert condTrue();
        //start new buffer
        m_noPrint = false;
        final CharBuffer newBuf;
        try {
            newBuf = InputStream.create(fn.getFilename());
        } catch (Exception ex) {
            throw new ParseException("VPP-FILE-1", null, fn.getCanonicalPath(), "read");
        }
        print("`line 1 \"").print(fn.getCanonicalPath()).print("\" 1\n");
        if (null != m_buf) {
            if (m_bufStk.size() == stMaxInclDepth) {
                throw new ParseException("VPP-INCL-6", null, stMaxInclDepth);
            }
            m_bufStk.push(m_buf);
        }
        m_buf = newBuf;
    }

    /**
     * Upon EOF, we will pop a CharBuffer, or do nothing if stack empty.
     *
     * @return true if pop and new CharBuffer, else false.
     */
    private boolean pop() {
        assert condTrue();
        final boolean ok = !m_bufStk.empty();
        if (ok) {
            m_buf = m_bufStk.pop();
            m_noPrint = false;
            final String fname = File.getCanonicalPath(m_buf.getFileName());
            print("`line ").print(m_buf.getLine()).print(" \"").print(fname)
                    .print("\" 2\n");
        } else {
            m_buf = null;   //we're done
        }
        return ok;
    }

    //Maximum include nested depth.
    private static final int stMaxInclDepth = 16;

    /**
     * Indicate that last conditional `ifdef/`else/... evaluation is true so we
     * can print/evaluate `include, ...
     *
     * @return true if last conditional evaluated true.
     */
    private boolean condTrue() {
        return Helper.getTheOne().getConditionalAllow();
    }

    private final Stack<CharBuffer> m_bufStk = new Stack<>();
    private CharBuffer m_buf;
    private final PrintWriter m_oses[];
    //Disable printing during "in-between" processing.
    private boolean m_noPrint;
}
