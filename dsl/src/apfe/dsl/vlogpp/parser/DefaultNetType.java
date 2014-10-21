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
import apfe.runtime.CharSeq;
import apfe.runtime.Memoize;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Sequence;
import apfe.runtime.Util;

/**
 *
 * @author gburdell
 */
public class DefaultNetType extends Acceptor {

    @Override
    protected boolean accepti() {
        //DefaultNetType <- "`default_nettype" Spacing
        //  ("wire" / "tri0" / "tri1" / "wand" / "triand" / "wor" / "trior" 
        //  / "trireg" / "uwire" / "none" / "tri")
        PrioritizedChoice p1 = new PrioritizedChoice(new CharSeq("wire"),
                new CharSeq("tri0"), new CharSeq("tri1"),
                new CharSeq("wand"), new CharSeq("triand"),
                new CharSeq("wor"), new CharSeq("trior"), new CharSeq("trireg"),
                new CharSeq("uwire"), new CharSeq("none"), new CharSeq("tri"));
        Sequence s1 = new Sequence(new CharSeq("`default_nettype"),
                new Spacing(), p1);
        boolean match = (null != (s1 = match(s1)));
        if (match) {
            m_type = Util.extractEleAsString(s1, 2);
            m_text = super.toString();
        }
        return match;
    }

    private String m_type;
    private String m_text;

    @Override
    public String toString() {
        return m_text;
    }

    @Override
    public Acceptor create() {
        return new DefaultNetType();
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
     * Memoize for all instances of DefaultNetType.
     */
    private static Memoize stMemo = new Memoize();
}
