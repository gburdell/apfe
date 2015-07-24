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

import gblib.Util.Pair;
import static gblib.Util.invariant;
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

    //`ifdef, `else, `elsif, `endif, `ifndef
    static final Pattern stPatt1
            = Pattern.compile("[ \t]*((`(ifn?def|elsif)[ \t]+.*\\s)|(`(else|endif)([ \t]+.*)?\\s))");
    static final Pattern stPatt2
            = Pattern.compile("[ \t]*`(ifn?def|elsif|else|endif)[ \t]*([_a-zA-Z][_a-zA-Z0-9]*)?(.*)(\\s)");

    private TicConditional(final SourceFile src, final Matcher matcher) throws ParseError {
        if (!matcher.matches()) {
            throw new ParseError("VPP-COND-1", src.getLocation());
        }
        m_src = src;
        m_matcher = matcher;
        m_echoOn = m_src.setEchoOn(false);
    }

    private final SourceFile m_src;
    private final Matcher m_matcher;
    private final boolean m_echoOn;
    private EType m_type;
    private String m_macroNm;

    static TicConditional parse(final SourceFile src, final Matcher matcher) throws ParseError {
        TicConditional cond = new TicConditional(src, matcher);
        cond.parse();
        return cond;
    }

    public EType getType() {
        return m_type;
    }
    
    public String getMacroNm() {
        return m_macroNm;
    }
    
    private void parse() throws ParseError {
        m_src.accept(m_matcher.start(1));
        final String directive = m_matcher.group(1);
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
        if ((null != m_macroNm) && !m_macroNm.isEmpty() && (EType.eEndif==m_type || EType.eElse==m_type)) {
            throw new ParseError("VPP-COND-2", m_src.getLocation(), m_macroNm, directive);
        }
        int m = m_matcher.start(3);
        m_src.accept(m_matcher.start(3) - m_matcher.start(2));
        m_src.setEchoOn(m_echoOn);
    }
}
