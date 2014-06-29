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

import apfe.dsl.vlogpp.Main;
import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer;
import apfe.runtime.CharSeq;
import apfe.runtime.Memoize;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;
import java.util.LinkedList;
import java.util.List;

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
        Main main = Main.getTheOne();
        if (m_ifdef) {
            main.ticIfdef(m_id);
        } else {
            main.ticIfndef(m_id);
        }
        ConditionalLines cl1 = new ConditionalLines();
        match &= (null != (cl1 = match(cl1)));
        if (!match) {
            return false;
        }
        m_condLines = cl1.toString();
        //("`elsif" Spacing Identifier ConditionalLines)*
        //Process as a loop here so we can interject action
        StringBuilder sb = null;
        while (true) {
            Sequence s1 = new Sequence(new CharSeq("`elseif"), new Spacing(),
                    new Identifier());
            if (null != (s1 = match(s1))) {
                String eifId = Util.extractEleAsString(s1, 2);
                main.ticElsif(eifId);
                ConditionalLines clns = new ConditionalLines();
                assert null != (clns = match(clns));
                if (null == sb) {
                    sb = new StringBuilder();
                }
                sb.append(clns.toString());
            } else {
                break; //while
            }
        }
        if (null != sb) {
            m_elsifLines = sb.toString();
        }
        //("`else" ConditionalLines)?
        if (matchTrue(new CharSeq("`else"))) {
            main.ticElse();
            ConditionalLines cl2 = new ConditionalLines();
            assert (null != (cl2 = match(cl2)));
            m_else = cl2.toString();
        }
        match &= (new CharSeq("`endif")).acceptTrue();
        if (match) {
            main.ticEndif();
            /**
             * NOTE: m_str may not be correct depending on how the conditionals
             * get expanded.  Though guess it should be right.
             */
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

    @Override
    protected void memoize(CharBuffer.Marker mark, CharBuffer.Marker endMark) {
        stMemo.add(mark, this, endMark);
    }

    @Override
    protected Memoize.Data hasMemoized(CharBuffer.Marker mark) {
        return stMemo.memoized(mark);
    }
    /**
     * Memoize for all instances of TicConditional.
     */
    private static Memoize stMemo = new Memoize();
}
