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

public class Bool extends Acceptor {

    public Bool() {
    }

    @Override
    protected boolean accepti() {
        // Bool <- K_TRUE / K_FALSE
        Operator op = new Operator(Operator.EOp.K_TRUE);
        boolean match = (null != (op = match(op)));
        if (!match) {
            op = new Operator(Operator.EOp.K_FALSE);
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
        return new Bool();
    }
}
