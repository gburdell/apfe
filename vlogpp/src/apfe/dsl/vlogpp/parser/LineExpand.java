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

import apfe.dsl.vlogpp.Helper;
import apfe.runtime.Acceptor;
import apfe.runtime.Marker;
import apfe.runtime.CharSeq;
import apfe.runtime.Memoize;

/**
 *
 * @author gburdell
 */
public class LineExpand extends Acceptor {

    @Override
    protected boolean accepti() {
        //LineExpand <- "`__LINE__"
        boolean match = (new CharSeq("`__LINE__")).acceptTrue();
        if (match) {
            m_text = super.toString();
            if (Helper.getTheOne().getConditionalAllow()) {
				;//TODO
			}
        }
        return match;
    }
    private String m_text;

    @Override
    public String toString() {
        return m_text;
    }
    @Override
    public Acceptor create() {
        return new LineExpand();
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
     * Memoize for all instances of LineExpand.
     */
    private static Memoize stMemo = new Memoize();
}
