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
package apfe.dsl.vlogpp.parser;

import apfe.dsl.vlogpp.Helper;
import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer.Marker;
import apfe.runtime.CharSeq;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Sequence;
import apfe.runtime.Util;

/**
 *
 * @author gburdell
 */
public class TicConditional extends Acceptor {

    @Override
    protected boolean accepti() {
        /*
         TicConditional <- ("`ifdef" / "ifndef") Spacing Identifier ConditionalLines
         ("`elsif" Spacing Identifier ConditionalLines)*
         ("`else" ConditionalLines)?
         "`endif"
         */
        PrioritizedChoice pc1 = new PrioritizedChoice(new CharSeq("`ifdef"),
                new CharSeq("`ifndef"));
        boolean match = (null != (pc1 = match(pc1)));
        if (!match) {
            return false;
        }
        m_ifdef = (0 == pc1.whichAccepted());
        match &= (new Spacing()).acceptTrue();
        if (!match) {
            return false;
        }
        Identifier id = new Identifier();
        match &= (null != (id = match(id)));
        if (!match) {
            return false;
        }
        m_id = id.toString();
        Helper main = Helper.getTheOne();
        if (m_ifdef) {
            main.ticIfdef(m_id);
        } else {
            main.ticIfndef(m_id);
        }
        main.replace(super.getStartMark());
        ConditionalLines cl1 = new ConditionalLines();
        Marker curr = getCurrentMark();
        match &= (null != (cl1 = match(cl1)));
        if (!match) {
            return false;
        }
        m_condLines = cl1.toString();
        if (false == main.getConditionalAllow()) {
            main.replace(curr);
        }
        //TODO: if we are not passing, then push spaces or `line
        //to replace [curr,getCurrentMark())

        //("`elsif" Spacing Identifier ConditionalLines)*
        //Process as a loop here so we can interject action
        StringBuilder sb = null;
        while (true) {
            curr = getCurrentMark();
            Sequence s1 = new Sequence(new CharSeq("`elseif"), new Spacing(),
                    new Identifier());
            if (null != (s1 = match(s1))) {
                String eifId = Util.extractEleAsString(s1, 2);
                main.ticElsif(eifId);
                main.replace(curr);  //remove `elseif
                curr = getCurrentMark();
                ConditionalLines clns = new ConditionalLines();
                assert null != (clns = match(clns));
                if (null == sb) {
                    sb = new StringBuilder();
                }
                sb.append(clns.toString());
                if (false == main.getConditionalAllow()) {
                    main.replace(curr);
                }
            } else {
                break; //while
            }
        }
        if (null != sb) {
            m_elsifLines = sb.toString();
        }
        //("`else" ConditionalLines)?
        curr = getCurrentMark();
        if (matchTrue(new CharSeq("`else"))) {
            main.ticElse();
            main.replace(curr); //rm `else
            curr = getCurrentMark();
            ConditionalLines cl2 = new ConditionalLines();
            assert (null != (cl2 = match(cl2)));
            m_else = cl2.toString();
            if (false == main.getConditionalAllow()) {
                main.replace(curr);
            }
        }
        curr = getCurrentMark();
        match &= (new CharSeq("`endif")).acceptTrue();
        if (match) {
            main.ticEndif();
            main.replace(curr);
            m_str = super.toString();
        }
        return match;
    }

    private boolean m_ifdef;
    private String m_id;
    private String m_condLines;
    private String m_elsifLines;
    private String m_else;
    private String m_str;

    @Override
    public String toString() {
        return m_str;
    }

    @Override
    public Acceptor create() {
        return new TicConditional();
    }
}
