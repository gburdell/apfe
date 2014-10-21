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
package apfe.peg;

import apfe.peg.generate.GenJava;
import apfe.peg.generate.Main;
import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer.Marker;
import apfe.runtime.Memoize;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;
import java.util.LinkedList;
import java.util.List;

public class Expression extends Acceptor implements GenJava.IGen {

    public Expression() {
    }

    @Override
    public GenJava genJava(GenJava j) {
        assert (null != getSequences() && (0 < getSequences().size()));
        if (1 == getSequences().size()) {
            j = j.append(getSequences().get(0));
        } else {
            if (Main.stGenMaze) {
                final String cc = (Main.stGenMaze) ? "Alternates" : "PrioritizedChoice";
                //TODO: this is the lazy/inefficient way
                j = j.funcCall("new " + cc, getSequences());
            } else {
                j = j.genChoiceClause(getSequences());
            }
        }
        return j;
    }

    private static final Operator stSlash = new Operator(Operator.EOp.SLASH);
    private static final Operator stBar = new Operator(Operator.EOp.BAR);

    @Override
    protected boolean accepti() {
        //Expression <- PegSequence ((SLASH / BAR) PegSequence)*
        PrioritizedChoice a1 = new PrioritizedChoice(stSlash.create(), stBar.create());
        Sequence s1 = new Sequence(a1, new PegSequence());
        Repetition r1 = new Repetition(s1, Repetition.ERepeat.eZeroOrMore);
        Sequence s2 = new Sequence(new PegSequence(), r1);
        boolean match = (null != (s2 = match(s2)));
        if (match) {
            m_seqs = new LinkedList<>();
            PegSequence ps = Util.extractEle(s2, 0);
            m_seqs.add(ps);
            r1 = Util.extractEle(s2, 1);
            Util.extractList(r1, 1, m_seqs);
        }
        return match;
    }

    private List<PegSequence> m_seqs;

    public List<PegSequence> getSequences() {
        return m_seqs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (PegSequence a : getSequences()) {
            if (0 < sb.length()) {
                sb.append("\n  ").append(stDelim.toString()).append(' ');
            }
            sb.append(a);
        }
        return sb.toString();
    }

    public static final Acceptor stDelim = Main.stGenMaze ? stBar : stSlash;

    @Override
    public Acceptor create() {
        return new Expression();
    }

    @Override
    protected void memoize(Marker mark, Marker endMark) {
        stMemo.add(mark, this, endMark);
    }

    @Override
    protected Memoize.Data hasMemoized(Marker mark) {
        return stMemo.memoized(mark);
    }
    /**
     * Memoize for all instances of Expression.
     */
    private static Memoize stMemo = new Memoize();
}
