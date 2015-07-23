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

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static apfe.v2.vlogpp.FileCharReader.EOF;
import static apfe.v2.vlogpp.FileCharReader.NL;
import gblib.Pair;

/**
 *
 * @author gburdell
 */
class TicConditional {

    //`ifdef, `else, `elsif, `endif, `ifndef
    static final Pattern stPatt1 = 
            Pattern.compile("[ \t]*((`(ifn?def|elsif)[ \t]+.*\\s)|(`(else|endif)([ \t]+.*)?\\s+))");
    static final Pattern stPatt2 = Pattern.compile("[ \t]*(`define)[ \t]+([_a-zA-Z][_a-zA-Z0-9]*)(.*)(\\s)");

    private TicConditional(final SourceFile src, final Matcher matcher) throws ParseError {
        if (! matcher.matches()) {
            throw new ParseError("VPP-DEFN-2", src.getLocation());
        }
        m_src = src;
        m_matcher = matcher;
        m_echoOn = m_src.setEchoOn(false);
    }

    private int[] m_started;
    private final SourceFile m_src;
    private final Matcher m_matcher;
    private final boolean m_echoOn;

    static void parse(final SourceFile src, final Matcher matcher) throws ParseError {
        TicConditional ticDefn = new TicConditional(src, matcher);
        ticDefn.parse();
    }

    private void parse() throws ParseError {
        int n = m_matcher.start(1);
        m_src.accept(n);
        m_started = m_src.getStartMark();
        m_macroName = m_matcher.group(2);
        int m = m_matcher.start(3);
        m_src.accept(m - n);
        //we are just past macroNm
        //The left parenthesis shall follow the text macro name immediately
        //The `define macro text can also include `", `\`", and ``
        if ('(' == la()) {
            formalArguments();
        }
        m_arg.setLength(0);
        boolean loop = true;
        int c;
        while (loop) {
            c = next();
            if ('\\' == c) {
                c = next();
                if (NL == c) {
                    append(NL);
                } else {
                    append('\\').append(c);
                }
            } else {
                loop = (NL != c);
                if (loop) {
                    if (EOF == c) {
                        throw new ParseError("VPP-EOF-2", getLocation(),
                                "`define", m_started);
                    }
                    append(c);
                }
            }
        }
        m_macroText = m_arg.toString().trim();
        if (null != m_formalArgs) {
            addMarkers();
        }
        m_src.setEchoOn(m_echoOn);
    }

    private final StringBuilder m_arg = new StringBuilder();

    private static final String[] stBalanced = new String[]{
        "({[\"", ")}]\""
    };

    private int next() {
        return m_src.next();
    }

    private int la(int n) {
        return m_src.la(n);
    }

    private int la() {
        return la(0);
    }

    private StringBuilder append(final int c) {
        return m_arg.append((char) c);
    }

    private String getLocation() {
        return m_src.getLocation();
    }

    private char parse(final char[] returnOn, final int depth) {
        int c, n;
        while (true) {
            c = next();
            switch (c) {
                case EOF:
                    //todo: throw...
                    break;
                case NL:
                    //todo: throw...
                    break;
                case '\\':
                    append(c).append(next());
                    break;
                case '`':
                    // `", `\`", and ``
                    if ('`' == la() || '"' == la()) {
                        append(c).append(next());
                    } else if ('\\' == la() && '`' == la(1) && '"' == la(2)) {
                        append(c).append(next()).append(next()).append(next());
                    } else {
                        append(c);
                    }
                    break;
                default:
                    n = search(returnOn, (char) c);
                    if (0 <= n) {
                        return (char) c;
                    }
                    append(c);
                    n = stBalanced[0].indexOf(c);
                    if (0 <= n) {
                        parse(new char[]{stBalanced[1].charAt(n)}, depth + 1);
                        append(next());
                    }
            }
        }
    }

}
