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
package apfe.dsl.vlogpp;

import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer;
import apfe.runtime.CharSeq;
import apfe.runtime.Memoize;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Repetition;
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
        ConditionalLines cl1 = new ConditionalLines();
        match &= (null != (cl1 = match(cl1)));
        if (!match) {
            return false;
        }
        m_condLines = cl1.toString();
        //("`elsif" Spacing Identifier ConditionalLines)*
        Sequence s1 = new Sequence(new CharSeq("`elseif"), new Spacing(),
                new Identifier(), new ConditionalLines());
        Repetition r1 = new Repetition(s1, Repetition.ERepeat.eZeroOrMore);
        match &= (null != (r1 = match(r1)));
        if (!match) {
            return false;
        }
        if (0 < r1.sizeofAccepted()) {
            m_elseifs = r1;
        }
        //("`else" ConditionalLines)?
        r1 = new Repetition(new Sequence(new CharSeq("`else"),
                new ConditionalLines()), Repetition.ERepeat.eOptional);
        match &= (null != (r1 = match(r1)));
        if (!match) {
            return false;
        }
        if (0 < r1.sizeofAccepted()) {
            m_else = Util.extractEleAsString((Sequence) r1.getOnlyAccepted(), 1);
        }
        match &= (new CharSeq("`endif")).acceptTrue();
        if (match) {
            m_str = super.toString();
        }
        return match;
    }
    private boolean m_ifdef;
    private String m_id;
    private String m_condLines;
    private Repetition m_elseifs;
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
