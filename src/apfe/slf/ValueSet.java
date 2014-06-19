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
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;

public class ValueSet extends Acceptor {

    public ValueSet() {
    }

    @Override
    protected boolean accepti() {
        //ValueSet <- LCURLY KeyValue* RCURLY
        Sequence s1 = new Sequence(new Operator(Operator.EOp.LCURLY),
                new Repetition(new KeyValue(), Repetition.ERepeat.eZeroOrMore),
                new Operator(Operator.EOp.RCURLY));
        boolean match = (null != (s1 = match(s1)));
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
        return new ValueSet();
    }

}
