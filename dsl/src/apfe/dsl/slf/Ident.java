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
import apfe.runtime.CharBuffer.Marker;
import apfe.runtime.Memoize;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;
import java.util.List;

public class Ident extends Acceptor {

    public Ident() {
    }

    @Override
    protected boolean accepti() {
        // Identifier <- IdentStart IdentCont* Spacing
        IdentStart e1 = new IdentStart();
        Repetition e2 = new Repetition(new IdentCont(), Repetition.ERepeat.eZeroOrMore);
        Sequence seq = new Sequence(e1, e2, new Spacing());
        boolean match = (null != (seq = match(seq)));
        if (match) {
            e1 = Util.extractEle(seq, 0);
            m_literal = new StringBuilder(e1.toString());
            e2 = Util.extractEle(seq, 1);
            if (0 < e2.sizeofAccepted()) {
                List<IdentCont> l1 = Util.toList(e2.getAccepted());
                for (IdentCont ic : l1) {
                    m_literal.append(ic);
                }
            }
            m_spc = Util.extractEle(seq, 2);
        }
        return match;
    }

    private Spacing  m_spc;
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(m_literal.toString());
        s.append(m_spc);
        return s.toString();
    }
    
    public String getId() {
        return m_literal.toString();
    }
    
    private StringBuilder m_literal = new StringBuilder();

    @Override
    public Acceptor create() {
        return new Ident();
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
     * Memoize for all instances of Ident.
     */
    private static Memoize stMemo = new Memoize();
}
