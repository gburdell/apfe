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
import apfe.runtime.Marker;
import apfe.runtime.CharSeq;
import apfe.runtime.Memoize;
import apfe.runtime.NotPredicate;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;
import java.util.List;

public class PegClass extends Acceptor implements GenJava.IGen {

    public PegClass() {
    }

    @Override
    public GenJava genJava(GenJava j) {
        gblib.Util.assertFalse(Main.stGenMaze, "PegClass not supported for maze");
        assert (0 < getRanges().size());
        return j.funcCall("new CharClass", getRanges());
    }

    @Override
    protected boolean accepti() {
        // Class <- '[' (!']' Range)* ']' Spacing
        Repetition r1 = new Repetition(new Sequence(
                new NotPredicate(new CharSeq(']')),
                new Range()), Repetition.ERepeat.eZeroOrMore);
        Sequence sq1 = new Sequence(new CharSeq('['), r1, new CharSeq(']'), new Spacing());
        boolean match = (null != (sq1 = match(sq1)));
        if (match) {
            r1 = Util.extractEle(sq1, 1);
            m_rng = Util.extractList(r1, 1);
        }
        return match;
    }

    private List<Range> m_rng;

    public List<Range> getRanges() {
        return m_rng;
    }

    @Override
    public String toString() {
        if (null == m_rng) {
            return null;
        }
        StringBuilder bld = new StringBuilder();
        Util.toString(false, bld.append('['), getRanges()).append(']');
        return bld.toString();
    }

    @Override
    public Acceptor create() {
        return new PegClass();
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
     * Memoize for all instances of PegClass.
     */
    private static Memoize stMemo = new Memoize();
}
