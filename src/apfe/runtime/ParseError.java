/*
 * The MIT License
 *
 * Copyright 2013 gburdell.
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
package apfe.runtime;

import gblib.MessageMgr.Message;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import static apfe.runtime.CharBuffer.EOF;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Parser error.
 *
 * @author gburdell
 */
public class ParseError {

    public static void setSkipErrorHints(String... s) {
        stSkipThese = new HashSet<>(Arrays.asList(s));
    }
    
    private static HashSet<String> stSkipThese = new HashSet<>();

    private enum EType {

        eFoundExpecting
    }

    private ParseError(EType type, String... args) {
        m_type = type;
        m_loc = State.getTheOne().getCurrentMark();
        if (EType.eFoundExpecting == m_type) {
            m_args = new String[]{args[0]};
            m_expecting = new LinkedList<>();
            {
                final String cand = clean(args[1]);
                if (!stSkipThese.contains(cand))  {
                    m_expecting.add(cand);
                }
            }
        } else {
            m_args = args;
        }
    }

    private static String clean(String s) {
        return s.replace("\t", "\\t").replace("\n", "\\n");
    }

    private final EType m_type;
    private final String[] m_args;
    private final Marker m_loc;
    private List<String> m_expecting;

    public static void push(String... args) {
        ParseError p = new ParseError(EType.eFoundExpecting, args);
        push(p);
    }

    public static void push(char c, String... args) {
        String s = (EOF != c) ? String.valueOf(c) : "<EOF>";
        String nargs[] = new String[1 + args.length];
        nargs[0] = s;
        System.arraycopy(args, 0, nargs, 1, args.length);
        ParseError p = new ParseError(EType.eFoundExpecting, nargs);
        push(p);
    }

    public static void push(final ParseError fail) {
        m_fails.add(fail);
    }

    public static void reset() {
        if (null == m_fails) {
            m_fails = new Stack<>();
        } else {
            m_fails.clear();
        }
    }

    private static Stack<ParseError> m_fails;

    static {
        reset();
    }

    /**
     * Go through stack entries and replace (all) with the deepest fail. Augment
     * expected sets where applicable.
     */
    public static void reduce() {
        if (1 < m_fails.size()) {
            //1st pass: find deepest
            ParseError deepest = null;
            for (ParseError e : m_fails) {
                if (null == deepest) {
                    deepest = e;
                } else if (deepest.m_loc.getPos() < e.m_loc.getPos()) {
                    deepest = e;
                }
            }
            //2nd pass: merge any at same position
            assert null != deepest;
            if (EType.eFoundExpecting == deepest.m_type) {
                for (ParseError e : m_fails) {
                    if (deepest.m_loc.getPos() == e.m_loc.getPos()) {
                        if (EType.eFoundExpecting == e.m_type) {
                            /*TODO: assert deepest.m_args[0].equals(e.m_args[0]);*/
                            deepest.m_expecting = gblib.Util.union(deepest.m_expecting, e.m_expecting);
                        }
                    }
                }
            }
            m_fails.clear();
            m_fails.add(deepest);
        }
    }

    public static String getTopMessage() {
        final Message msg = getTopMessage2();
        return (null != msg) ? msg.toString() : "";
    }

    public static void printTopMessage() {
        final Message msg = getTopMessage2();
        if (null != msg) {
            gblib.MessageMgr.print(msg);
        }
    }

    private static Message getTopMessage2() {
        Message msg = null;
        if (hasError()) {
            msg = new Message('E', stErrCode, getMessageArgs());
        }
        return msg;
    }

    public static Object[] getMessageArgs() {
        assert hasError();
        ParseError e = m_fails.peek();
        assert EType.eFoundExpecting == e.m_type;
        final String fn = e.m_loc.getFileName();
        final String loc = e.m_loc.toString();  //line:col
        return new Object[]{fn, loc, e.m_args[0], e.m_expecting};
    }

    public static boolean hasError() {
        return !m_fails.empty();
    }

    public static final String stErrCode = "PARSE-5";
}
