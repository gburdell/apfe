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
import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer.Marker;
import apfe.runtime.Memoize;
import apfe.runtime.NotPredicate;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Sequence;
import apfe.runtime.Util;

public class Primary extends Acceptor implements GenJava.IGen {

    public static enum EType {

        eIdentifier, eExpression, eLiteral, eClass, eDot
    };

    private static final EType stVals[] = EType.values();

    public Primary() {
    }

    @Override
    public GenJava genJava(GenJava j) {
        switch (getType()) {
            case eDot:
                j.append("new CharClass(ICharClass.IS_ANY)");
                break;
            case eLiteral:
            case eClass:        //fall through
            case eIdentifier:   //fall through
                GenJava.IGen g = Util.downCast(getEle());
                g.genJava(j);
                break;
            default:
                assert (false); //TODO
        }
        return j;
    }

    private static final Operator stOpen = new Operator(Operator.EOp.OPEN),
            stClose = new Operator(Operator.EOp.CLOSE);

    @Override
    protected boolean accepti() {
        // Primary <- Identifier !LEFTARROW 
        //            / OPEN Expression CLOSE 
        //            / Literal / Class / DOT
        PrioritizedChoice pc1 = new PrioritizedChoice(new PrioritizedChoice.Choices() {
            @Override
            public Acceptor getChoice(int ix) {
                Acceptor a = null;
                switch (ix) {
                    case 0:
                        a = new Sequence(new Identifier(),
                                new NotPredicate(new Operator(Operator.EOp.LEFTARROW)));
                        break;
                    case 1:
                        a = new Sequence(stOpen.create(),
                                new Expression(), stClose.create());
                        break;
                    case 2:
                        a = new Literal();
                        break;
                    case 3:
                        a = new PegClass();
                        break;
                    case 4:
                        a = new Operator(Operator.EOp.DOT);
                        break;
                }
                return a;
            }
        });
        boolean match = (null != (pc1 = match(pc1)));
        if (match) {
            int ix = pc1.whichAccepted();
            m_type = stVals[ix];
            switch (m_type) {
                case eLiteral:
                case eClass:
                case eDot:
                    m_ele = pc1.getAccepted();
                    break;
                case eIdentifier: {
                    Sequence s1 = Util.downCast(pc1.getAccepted());
                    m_ele = Util.extractEle(s1, 0);
                }
                break;
                case eExpression: {
                    Sequence s1 = Util.downCast(pc1.getAccepted());
                    m_ele = Util.extractEle(s1, 1);
                }
                break;
                default:
                    assert false;
            }
        }
        return match;
    }

    public EType getType() {
        return m_type;
    }

    public Acceptor getEle() {
        return m_ele;
    }

    private EType m_type;
    private Acceptor m_ele;

    @Override
    public String toString() {
        String s;
        if (EType.eExpression != m_type) {
            s = m_ele.toString();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(stOpen).append(m_ele).append(stClose);
            s = sb.toString();
        }
        return s;
    }

    @Override
    public Acceptor create() {
        return new Primary();
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
     * Memoize for all instances of Primary.
     */
    private static Memoize stMemo = new Memoize();
}
