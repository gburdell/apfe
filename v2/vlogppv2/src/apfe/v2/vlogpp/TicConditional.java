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

import gblib.Util;
import static gblib.Util.invariant;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author gburdell
 */
class TicConditional {

    public static enum EType {

        eIfdef, eIfndef, eElsif, eElse, eEndif
    }

    private static class State {
        private static enum EAlive {
            eNotYet, //nothing taken yet
            eActive, //currently taking/alive
            eDone   //eActive->eDone; or start new if(n)def w/in !eActive
        }
        State(final FileLocation started, final EType type,
                final EAlive alive) {
            m_where = type;
            m_alive = alive;
            m_started = started;
        }
        private EType m_where;
        private EAlive m_alive;
        private final FileLocation m_started;
    }

    //`ifdef, `else, `elsif, `endif, `ifndef
    static final Pattern stPatt1
            = Pattern.compile("[ \t]*((`(ifn?def|elsif)[ \t]+.*\\s)|(`(else|endif)([ \t]+.*)?\\s))");
    static final Pattern stPatt2
            = Pattern.compile("[ \t]*`(ifn?def|elsif|else|endif)[ \t]*([_a-zA-Z][_a-zA-Z0-9]*)?(.*)(\\s)");

    private TicConditional(final SourceFile src, final Matcher matcher) throws ParseError {
        if (!matcher.matches()) {
            throw new ParseError("VPP-COND-1", src);
        }
        m_src = src;
        m_matcher = matcher;
        m_echoOn = m_src.setEchoOn(false);
    }

    private final SourceFile m_src;
    private final Matcher m_matcher;
    private final boolean m_echoOn;
    private EType m_type;
    private String m_macroNm, m_directive;
    private FileLocation m_started;

    static void process(final SourceFile src, final Matcher matcher) throws ParseError {
        TicConditional cond = new TicConditional(src, matcher);
        cond.parse();
        cond.update();
    }

    public EType getType() {
        return m_type;
    }

    public String getMacroNm() {
        return m_macroNm;
    }

    private void parse() throws ParseError {
        m_src.accept(m_matcher.start(1));
        m_started = m_src.getFileLocation();
        final String directive = m_matcher.group(1);
        m_directive = "`" + directive;
        switch (directive) {
            case "ifdef":
                m_type = EType.eIfdef;
                break;
            case "ifndef":
                m_type = EType.eIfndef;
                break;
            case "elsif":
                m_type = EType.eElsif;
                break;
            case "else":
                m_type = EType.eElse;
                break;
            case "endif":
                m_type = EType.eEndif;
                break;
            default:
                invariant(false);
        }
        m_macroNm = m_matcher.group(2);
        m_src.accept(m_matcher.start(2) - m_matcher.start(1));
        if ((null != m_macroNm) && !m_macroNm.isEmpty() && (EType.eEndif == m_type || EType.eElse == m_type)) {
            throw new ParseError("VPP-COND-2", m_src, m_macroNm, directive);
        }
        int m = m_matcher.start(3);
        m_src.accept(m_matcher.start(3) - m_matcher.start(2));
    }

    private void update() throws ParseError {
        //update SourceFile state
        Stack<State> stack;
        if (null != m_src.m_cond) {
            stack = (Stack<State>) m_src.m_cond;
        } else {
            m_src.m_cond = stack = new Stack<>();
        }
        //Account for nested ifdef.
        //A new if(n)def inherits liveliness from enclosing.
        if (oneOf(getType(), EType.eIfdef, EType.eIfndef)) {
            //TODO
            if (stack.size() >= stMaxConditionalDepth) {
                throw new ParseError("VPP-COND-3", m_started, stMaxConditionalDepth);
            }
            //TODO: stack.push(new State(m_started, getType(), enclosingAlive, alive));
        } else if (stack.empty()) {
            throw new ParseError("VPP-COND-1", m_started);
        } else {
            final State top = stack.peek();
            switch (getType()) {
                case eElse:
                    switch (top.m_where) {
                        case eIfdef:
                        case eIfndef:
                            //flip the state
                            //TODO
                            top.m_where = getType();
                            break;
                        case eElsif:
                            //TODO
                            top.m_where = getType();
                            break;
                        default:
                            throw new ParseError("VPP-COND-4", m_src, m_directive, top.m_started);
                    }
                    break;
                case eElsif:
                    switch (top.m_where) {
                        case eIfdef:
                        case eIfndef:
                        case eElsif:
                            //TODO
                            top.m_where = getType();
                            break;
                        default:
                            throw new ParseError("VPP-COND-4", m_src, m_directive, top.m_started);
                    }
                    break;
                case eEndif:
                    stack.pop();
                    //TODO
                    break;
                default:
                    invariant(false);
            }
        }
        //TODO
        //TODO m_src.setEchoOn(alive);
    }

    private static boolean oneOf(final EType ele, final EType... in) {
        return (0 <= Util.linearSearch(in, ele));
    }

    public static final int stMaxConditionalDepth = 256;
}
