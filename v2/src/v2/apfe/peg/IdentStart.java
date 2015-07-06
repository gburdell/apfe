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
import v2.apfe.runtime.CharBufState;
import v2.apfe.runtime.CharBuffer;
import v2.apfe.runtime.Marker;
import v2.apfe.runtime.ICharClass;
import v2.apfe.runtime.Memoize;

public class IdentStart extends Acceptor {

    public IdentStart() {
    }

    @Override
    protected boolean accepti() {
        CharBuffer buf = CharBufState.asMe().getBuf();
        // IdentStart <- [a-zA-Z_]
        char c = buf.la();
        boolean match = ICharClass.IS_ALPHA.inClass(c) || (c == '_');
        if (match) {
            buf.accept();
            m_literal.append(c);
        }
        return match;
    }

    @Override
    public String toString() {
        return m_literal.toString();
    }
    
    private StringBuilder m_literal = new StringBuilder();

    @Override
    public Acceptor create() {
        return new IdentStart();
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
     * Memoize for all instances of Space.
     */
    private static Memoize stMemo = new Memoize();
}
