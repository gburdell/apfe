/*
 * The MIT License
 *
 * Copyright 2014 gburdell.
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
package apfe.dsl.vlogpp.parser;

import apfe.runtime.Acceptor;
import apfe.runtime.Marker;
import apfe.runtime.CharClass;
import apfe.runtime.CharSeq;
import apfe.runtime.ICharClass;
import apfe.runtime.Memoize;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;

/**
 *
 * @author gburdell
 */
public class VString extends Acceptor {

    @Override
    protected boolean accepti() {
        //'"' (NotChar1 .)* '"'
        Repetition r1 = new Repetition(
                new Sequence(new NotChar1(), new CharClass(ICharClass.IS_ANY)),
                Repetition.ERepeat.eZeroOrMore);
        Sequence s1 = new Sequence(new CharSeq('"'), r1, new CharSeq('"'));
        boolean match = (null != (s1 = match(s1)));
        if (match) {
            m_str = super.toString();
            m_str = m_str.substring(1, m_str.length()-1);
        }
        return match;
    }

    private String m_str;

    @Override
    public String toString() {
        return m_str;
    }

    @Override
    public Acceptor create() {
        return new VString();
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
     * Memoize for all instances of VStingr.
     */
    private static Memoize stMemo = new Memoize();
}
