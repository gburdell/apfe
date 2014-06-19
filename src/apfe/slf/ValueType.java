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
package apfe.slf;

import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer;
import apfe.runtime.CharClass;
import apfe.runtime.Memoize;
import apfe.runtime.NotPredicate;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;

public class ValueType extends Acceptor {

    public ValueType() {
    }

    @Override
    protected boolean accepti() {
        /*
         * ValueType <- Expr
         /  VIdent Bus?
         /  STRING
         /  Number (Unit !COLON)?
         /  Bool
         */
        PrioritizedChoice pc1 = new PrioritizedChoice(new PrioritizedChoice.Choices() {
            @Override
            public Acceptor getChoice(int ix) {
                Acceptor a = null;
                switch (ix) {
                    case 0:
                        a = new Expr();
                        break;
                    case 1:
                        a = new Sequence(new VIdent(), new Repetition(new Bus(), 
                                Repetition.ERepeat.eOptional));
                        break;
                    case 2:
                        a = new SlfString();
                        break;
                    case 3: {
                        PrioritizedChoice pc = new PrioritizedChoice(
                                new Operator(Operator.EOp.COLON),
                                new Operator(Operator.EOp.LPAREN));
                        a = new Sequence(new Number(), 
                                new Repetition(new Sequence(
                                new Unit(), 
                                new NotPredicate(pc)),
                                Repetition.ERepeat.eOptional));
                    }
                        break;
                    case 4:
                        a = new Bool();
                        break;
                    case 5:
                        a = null;//inPredicate() ? null : new Error();
                        break;
                }
                return a;
            }
        });
        boolean match = (null != (pc1 = match(pc1)));
        if (match) {
            //todo
        }
        return match;
    }
    private Operator m_val;

    @Override
    public String toString() {
        return m_val.toString();
    }

    @Override
    public Acceptor create() {
        return new ValueType();
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
     * Memoize for all instances of ValueType.
     */
    private static Memoize stMemo = new Memoize();

}
