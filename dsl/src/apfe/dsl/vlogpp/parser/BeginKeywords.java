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
import apfe.runtime.CharSeq;
import apfe.runtime.Memoize;
import apfe.runtime.Sequence;

/**
 *
 * @author gburdell
 */
public class BeginKeywords extends Acceptor {

    @Override
    protected boolean accepti() {
        //BeginKeywords <- "`begin_keywords" Spacing VString
        m_contents = new Sequence(new CharSeq("`begin_keywords"), new Spacing(),
                new VString());
        boolean match = (null != (m_contents = match(m_contents)));
        if (match) {
            m_text = super.toString();
        }
        return match;
    }
    private Sequence m_contents;
    private String m_text;

    @Override
    public String toString() {
        return m_text;
    }

    @Override
    public Acceptor create() {
        return new BeginKeywords();
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
     * Memoize for all instances of BeginKeywords.
     */
    private static Memoize stMemo = new Memoize();
}
