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
package v2.apfe.peg;

import v2.apfe.peg.Operator.EOp;
import v2.apfe.peg.generate.GenJava;
import v2.apfe.peg.generate.Main;
import v2.apfe.runtime.Acceptor;
import v2.apfe.runtime.Marker;
import v2.apfe.runtime.Memoize;
import v2.apfe.runtime.PrioritizedChoice;
import v2.apfe.runtime.Repetition;
import v2.apfe.runtime.Sequence;
import v2.apfe.runtime.Util;

public class Prefix extends Acceptor implements GenJava.IGen {

    public Prefix() {
    }

    @Override
    public GenJava genJava(GenJava j) {
        assert null == getAsgn();//not supported for now.
        if (null != getOp()) {
            gblib.Util.assertFalse(Main.stGenMaze, "Predicate() not supported for maze");
            j = j.append("new ")
                    .append((getOp().getOp() == EOp.AND) ? "And" : "Not")
                    .append("Predicate(");
        }
        j = j.append(getSuffix());
        if (null != getOp()) {
            j = j.append(")");
        }
        return j;
    }

    @Override
    protected boolean accepti() {
        // Prefix <- (AND / NOT)? Assign? Suffix
        Repetition r1 = new Repetition(new Assign(), Repetition.ERepeat.eOptional);
        Sequence s1 = new Sequence(
                new Repetition(new PrioritizedChoice(new PrioritizedChoice.Choices() {
                    @Override
                    public Acceptor getChoice(int ix) {
                        Acceptor a = null;
                        switch (ix) {
                            case 0:
                                a = new Operator(Operator.EOp.AND);
                                break;
                            case 1:
                                a = new Operator(Operator.EOp.NOT);
                                break;
                        }
                        return a;
                    }
                }),
                        Repetition.ERepeat.eOptional), r1, new Suffix());
        boolean match = (null != (s1 = match(s1)));
        if (match) {
            Repetition r2 = Util.extractEle(s1, 0);
            PrioritizedChoice pc1 = Util.getOnlyElement(r2);
            if (null != pc1) {
                m_op = gblib.Util.downCast(pc1.getAccepted());
            }
            r1 = Util.extractEle(s1, 1);
            if (0 < r1.sizeofAccepted()) {
                m_asgn = r1.getOnlyAccepted();
            }
            m_sfx = Util.extractEle(s1, 2);
        }
        return match;
    }
    private Operator m_op;
    private Suffix m_sfx;
    private Assign m_asgn;

    public Operator getOp() {
        return m_op;
    }

    public Assign getAsgn() {
        return m_asgn;
    }

    public Suffix getSuffix() {
        return m_sfx;
    }

    public boolean hasOp() {
        return (null != getOp());
    }

    @Override
    public String toString() {
        return Util.toString(m_op, m_asgn, m_sfx).toString();
    }

    @Override
    public Acceptor create() {
        return new Prefix();
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
     * Memoize for all instances of Prefix.
     */
    private static Memoize stMemo = new Memoize();
}
