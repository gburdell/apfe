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

import v2.apfe.runtime.Acceptor;
import v2.apfe.runtime.Marker;
import v2.apfe.runtime.Memoize;
import v2.apfe.runtime.Sequence;
import v2.apfe.runtime.Util;

public class Assign extends Acceptor {

    public Assign() {
    }

    private static final Operator stEq = new Operator(Operator.EOp.EQ);
    
    @Override
    protected boolean accepti() {
        //Assign <- Identifier EQ
        Sequence s1 = new Sequence(new Identifier(), stEq.create());
        boolean match = (null != (s1 = match(s1)));
        if (match) {
            m_id = Util.extractEle(s1, 0);
        }
        return match;
    }
    
    private Identifier m_id;
    
    public String getId() {
        return m_id.getId();
    }
    
    @Override
    public String toString() {
        return Util.toString(m_id, stEq).toString();
    }
    
    @Override
    public Acceptor create() {
        return new Assign();
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
     * Memoize for all instances of Assign.
     */
    private static Memoize stMemo = new Memoize();
}
