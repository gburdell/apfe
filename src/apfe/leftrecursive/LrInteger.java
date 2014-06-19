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
package apfe.leftrecursive;

import apfe.runtime.Acceptor;
import apfe.runtime.CharClass;
import apfe.runtime.ICharClass;
import apfe.runtime.Repetition;

public class LrInteger extends Acceptor {

    public LrInteger() {
    }

    @Override
    protected boolean accepti() {
        // Integer <- [0-9]+
        Repetition rp2 = new Repetition(new CharClass(ICharClass.IS_DIGIT), Repetition.ERepeat.eOneOrMore);
        boolean match = (null != (rp2 = match(rp2)));
        if (match) {
            m_val = Integer.parseInt(rp2.toString());
        }
        return match;
    }
    private Integer m_val;

    @Override
    public Acceptor create() {
        return new LrInteger();
    }

    @Override
    public String toString() {
        return m_val.toString();
    }

    @Override
    public String getNonTermID() {
        return getClass().getSimpleName();
    }
}
