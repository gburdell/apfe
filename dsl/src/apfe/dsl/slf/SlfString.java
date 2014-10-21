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
import apfe.runtime.CharClass;
import apfe.runtime.CharSeq;
import apfe.runtime.ICharClass;
import apfe.runtime.NotPredicate;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;

public class SlfString extends Acceptor {

    public SlfString() {
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(getLiteral());
        return Util.toString(s, m_spc).toString();
    }

    public String getLiteral() {
        return m_literal.toString();
    }

    @Override
    protected boolean accepti() {
        // STRING <- '"' (!'"' .)* '"' Spacing
        Sequence e2 = new Sequence(new NotPredicate(new CharSeq('"')), new CharClass(ICharClass.IS_ANY));
        Acceptor acc = new Sequence(new CharSeq('"'), new Repetition(e2, Repetition.ERepeat.eZeroOrMore),
                new CharSeq('"'), new Spacing());
        boolean match = (null != (acc = match(acc)));
        if (match) {
            //todo
        }
        return match;
    }
    private StringBuilder m_literal;
    private Spacing m_spc;

    @Override
    public Acceptor create() {
        return new SlfString();
    }

}
