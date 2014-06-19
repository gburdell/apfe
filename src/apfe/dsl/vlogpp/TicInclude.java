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
package apfe.dsl.vlogpp;

import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer;
import apfe.runtime.CharSeq;
import apfe.runtime.Memoize;
import apfe.runtime.Sequence;
import apfe.runtime.Util;

/**
 *
 * @author gburdell
 */
public class TicInclude extends Acceptor {

    private boolean match(char c1, char c2) {
        Sequence s1 = new Sequence(new CharSeq(c1), new FileName(), new CharSeq(c2));
        boolean m = (null != (s1 = match(s1)));
        if (m) {
            m_fname = Util.extractEleAsString(s1, 1);
        }
        return m;
    }

    @Override
    protected boolean accepti() {
        //TicInclude <- "`include" Spacing ('"' FileName '"' / '<' FileName '>')
        CharSeq c1 = new CharSeq("`include");
        boolean match = matchTrue(new Sequence(c1, new Spacing()));
        match &= match('"', '"') || match('<', '>');
        if (match) {
            //TODO: process fname
        }
        return match;
    }

    private String m_fname;

    @Override
    public Acceptor create() {
        return new TicInclude();
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
     * Memoize for all instances of TicInclude.
     */
    private static Memoize stMemo = new Memoize();
}
