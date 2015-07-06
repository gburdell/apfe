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

import v2.apfe.peg.generate.GenJava;
import v2.apfe.peg.generate.Main;
import v2.apfe.runtime.Acceptor;
import v2.apfe.runtime.Marker;
import v2.apfe.runtime.Memoize;
import v2.apfe.runtime.PrioritizedChoice;
import v2.apfe.runtime.Repetition;
import v2.apfe.runtime.Sequence;
import v2.apfe.runtime.Util;

public class Suffix extends Acceptor implements GenJava.IGen {

    public Suffix() {
    }

    @Override
    public GenJava genJava(GenJava j) {
        if (false == hasOp()) {
            return getPrimary().genJava(j);
        }
        Repetition.ERepeat rep = getOpAsRep();
        if (Main.stGenMaze) {
            if (rep == Repetition.ERepeat.eOptional) {
                j.append("new Optional(").append(getPrimary())
                        .append(") ");
            } else {
                j.append("new Repetition(").append(getPrimary());
                if (rep == Repetition.ERepeat.eOneOrMore) {
                    j.append(", true");
                }
                j.append(") ");
            }
        } else {
            j.append("new Repetition(");
            j.append(getPrimary()).append(", Repetition.ERepeat.")
                    .append(rep.name()).append(")");
        }
        return j;
    }

    @Override
    protected boolean accepti() {
        // Suffix <- Primary (QUESTION / STAR / PLUS)?
        Sequence s1 = new Sequence(new Primary(),
                new Repetition(new PrioritizedChoice(new PrioritizedChoice.Choices() {
                    @Override
                    public Acceptor getChoice(int ix) {
                        Acceptor a = null;
                        switch (ix) {
                            case 0:
                                a = new Operator(Operator.EOp.QUESTION);
                                break;
                            case 1:
                                a = new Operator(Operator.EOp.STAR);
                                break;
                            case 2:
                                a = new Operator(Operator.EOp.PLUS);
                                break;
                        }
                        return a;
                    }
                }),
                        Repetition.ERepeat.eOptional));
        boolean match = (null != (s1 = match(s1)));
        if (match) {
            Repetition r1 = Util.extractEle(s1, 1);
            PrioritizedChoice pc1 = Util.getOnlyElement(r1);
            if (null != pc1) {
                m_op = gblib.Util.downCast(pc1.getAccepted());
            }
            m_prim = Util.extractEle(s1, 0);
        }
        return match;
    }
    private Operator m_op;
    private Primary m_prim;

    public Operator getOp() {
        return m_op;
    }

    public Repetition.ERepeat getOpAsRep() {
        Repetition.ERepeat rval = null;
        if (hasOp()) {
            switch (getOp().getOp()) {
                case QUESTION:
                    rval = Repetition.ERepeat.eOptional;
                    break;
                case STAR:
                    rval = Repetition.ERepeat.eZeroOrMore;
                    break;
                case PLUS:
                    rval = Repetition.ERepeat.eOneOrMore;
                    break;
                default:
                    assert false;
            }
        }
        return rval;
    }

    public Primary getPrimary() {
        return m_prim;
    }

    public boolean hasOp() {
        return (null != getOp());
    }

    @Override
    public String toString() {
        return Util.toString(m_prim, m_op).toString();
    }

    @Override
    public Acceptor create() {
        return new Suffix();
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
     * Memoize for all instances of Suffix.
     */
    private static Memoize stMemo = new Memoize();
}
