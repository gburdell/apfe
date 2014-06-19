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
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;

/**
 *
 * @author gburdell
 */
public class FormalArgument extends Acceptor {

    @Override
    protected boolean accepti() {
        //FormalArgument <- Spacing Identifier (Spacing '=' (DefaultText)? )?
        boolean match = (new Spacing()).acceptTrue();
        if (match) {
            Identifier id = new Identifier();
            match = (null != (id = match(id)));
            if (match) {
                m_id = id.toString();
                DefaultText d = new DefaultText();
                Sequence s1 = new Sequence(new Spacing(), new CharSeq('='),
                        new Repetition(d, Repetition.ERepeat.eOptional));
                Repetition r1 = new Repetition(s1, Repetition.ERepeat.eOptional);
                match &= (null != (r1 = match(r1)));
                assert match; //should always happen
                if (match && (0 < r1.sizeofAccepted())) {
                    r1 = Util.extractEle((Sequence) r1.getOnlyAccepted(), 2);
                    m_dfltText = (0 < r1.sizeofAccepted()) ? r1.toString() : "";
                }
            }
            m_str = super.toString();
        }
        return match;
    }

    private String m_id;
    private String m_dfltText;
    private String m_str;

    @Override
    public String toString() {
        return m_str;
    }

    @Override
    public Acceptor create() {
        return new FormalArgument();
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
     * Memoize for all instances of FormalArgument.
     */
    private static Memoize stMemo = new Memoize();
}
