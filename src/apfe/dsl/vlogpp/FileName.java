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
import apfe.runtime.Memoize;
import apfe.runtime.Sequence;
import apfe.runtime.State;
import apfe.runtime.Util;

/**
 *
 * @author gburdell
 */
public class FileName extends Acceptor {

    @Override
    protected boolean accepti() {
        //FileName <- (!([">] / EOL / EOF) .)+
        boolean match = true;
        CharBuffer buf = State.getTheOne().getBuf();
        char ch;
        while (match) {
            ch = buf.la();
            switch (ch) {
                case '"': //fall through
                case '>': //fall through
                case CharBuffer.EOF: //fall through
                case CharBuffer.NL:
                    match = false;
                    break;
                default:
                    if (null == m_fname) {
                        m_fname = new StringBuilder();
                    }
                    m_fname.append(buf.accept());
            }
        }
        match = (null != m_fname) ? (0 < m_fname.length()) : false;
        return match;
    }

    private StringBuilder m_fname;
    
    @Override
    public String toString() {
        return (null != m_fname) ? m_fname.toString() : "";
    }

    @Override
    public Acceptor create() {
        return new FileName();
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
     * Memoize for all instances of FileName.
     */
    private static Memoize stMemo = new Memoize();
}
