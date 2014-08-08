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
import apfe.runtime.CharBuffer;
import apfe.runtime.CharClass;
import apfe.runtime.ICharClass;
import apfe.runtime.Memoize;
import apfe.runtime.Repetition;

/**
 *
 * @author gburdell
 */
public class Identifier extends Acceptor {

    @Override
    protected boolean accepti() {
        //Identifier <- [A-Za-z_] [A-Za-z_0-9]*
        CharClass c1 = new CharClass(ICharClass.IS_ALPHA, CharClass.matchOneOf("_"));
        boolean match = (null != (c1 = match(c1)));
        if (match) {
            StringBuilder sb1 = new StringBuilder(c1.toString());
            c1 = new CharClass(ICharClass.IS_ALPHANUM, CharClass.matchOneOf("_"));
            Repetition r1 = new Repetition(c1, Repetition.ERepeat.eZeroOrMore);
            match &= (null != (r1 = match(r1)));
            if (0 < r1.sizeofAccepted()) {
                sb1.append(r1.toString());
            }
            m_id = sb1.toString();
        }
        return match;
    }

    private String m_id;

    @Override
    public String toString() {
        return m_id;
    }

    @Override
    public Acceptor create() {
        return new Identifier();
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
     * Memoize for all instances of Identifier.
     */
    private static Memoize stMemo = new Memoize();
}
