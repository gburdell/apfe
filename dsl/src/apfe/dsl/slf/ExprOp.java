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
package apfe.dsl.slf;

import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer;
import apfe.runtime.Marker;
import apfe.runtime.Memoize;

public class ExprOp extends Acceptor {

    public ExprOp() {
    }

    @Override
    protected boolean accepti() {
        // ExprOp <- STAR / PLUS
        Operator op = new Operator(Operator.EOp.STAR);
        boolean match = (null != (op = match(op)));
        if (!match) {
            op = new Operator(Operator.EOp.PLUS);
            match = (null != (op = match(op)));
        }
        if (match) {
            m_val = op;
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
        return new ExprOp();
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
     * Memoize for all instances of ExprOp.
     */
    private static Memoize stMemo = new Memoize();
}
