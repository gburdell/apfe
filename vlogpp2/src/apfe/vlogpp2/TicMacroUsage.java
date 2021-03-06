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

import apfe.runtime.Acceptor;
import apfe.runtime.CharBufState;
import apfe.runtime.CharBuffer;
import apfe.runtime.CharClass;
import apfe.runtime.CharSeq;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class TicMacroUsage extends AcceptorWithLocation {

    public TicMacroUsage(CharBuffer.MarkerImpl loc) {
        super(loc);
    }

    private TicMacroUsage() {
        super(null);
    }

    private static final String stTokenPaste = "``";

    @Override
    protected boolean accepti() {
        //TicMacroUsage <- '``'? '`'Identifier (Spacing '(' ListOfActualArguments ')')?
        boolean hasTokPastePfx;
        //check for token paste prefix
        {
            Acceptor tp = new CharSeq(stTokenPaste);
            hasTokPastePfx = tp.acceptTrue();
            if (hasTokPastePfx) {
                if (CharBufState.asMe().getBuf().la() != '`') {
                    Helper.getTheOne().replace(super.getStartMark(), null);
                }
            }
        }
        Repetition ws = new Repetition(new CharClass(" \t"), Repetition.ERepeat.eZeroOrMore);
        Sequence s1 = new Sequence(new CharSeq('`'), ws, new Identifier());
        boolean match = (null != (s1 = match(s1)));
        if (match) {
            m_ident = Util.extractEleAsString(s1, 2);
            /*
             * Since LRM allows spacing between  `MACRO...(
             * we have pathological case where input has:
             *      `MACRO (p1,p2);
             * but, MACRO was not defined with parameters.
             * In that case, the '(' is not processed as part of the macro (here).
             */
            if (hasParms(m_ident)) {
                s1 = new Sequence(ws.create(), new CharSeq('('),
                        new ListOfActualArguments(), new CharSeq(')'));
                Repetition r1 = new Repetition(s1, Repetition.ERepeat.eOptional);
                match &= (null != (r1 = match(r1)));
                if (match) {
                    if (0 < r1.sizeofAccepted()) {
                        m_args = Util.extractEle((Sequence) r1.getOnlyAccepted(), 2);
                    }
                } else {
                    setParseError();
                    return false;
                }
            } else {
                ;//do nothing?
            }
            m_str = super.toString();
            match = expandMacro(getLocation(), hasTokPastePfx);
        }
        return match;
    }
    private String m_ident;
    private ListOfActualArguments m_args;
    private String m_str;

    private boolean hasParms(String nm) {
        return Helper.getTheOne().getMacroDefns().hasParameters(nm);
    }

    /**
     * Expand macro usage. The CharBuffer (in State) is then rewound to
     * beginning so any embedded TicMacroUsage are then subsequently expanded.
     *
     * @param loc location of instance.
     * @param hasTokPastePfx token paste detected immediately before macro.
     */
    private boolean expandMacro(final Location loc, final boolean hasTokPastePfx) {
        Helper mn = Helper.getTheOne();
        if (false == mn.getConditionalAllow()) {
            return true;
        }
        if (false == mn.isDefined(m_ident)) {
            //Helper.error("VPP-NODEFN", loc, m_ident);
            setError("VPP-NODEFN", m_ident);
            return false;
        }
        List<String> instArgs = (null != m_args) ? m_args.getArgs() : null;
        MacroDefns defns = mn.getMacroDefns();
        String expanded = defns.expandMacro(m_ident, instArgs, loc);
        if (null == expanded) {
            return false; //had error
        }
        if (hasTokPastePfx) {
            expanded = stTokenPaste + expanded;
        }
        {
            //NOTE: we'll only expect these funky chars during expansion
            String postFunky = expanded.replace(stTokenPaste, "")
                    .replace("`\\`\"", "\\\"")
                    .replace("`\"", "\"");
            expanded = postFunky;
        }
        mn.replace(super.getStartMark(), expanded);
        return true;
    }

    @Override
    public String toString() {
        return m_str;
    }

    @Override
    public Acceptor create() {
        return new TicMacroUsage();
    }
}
