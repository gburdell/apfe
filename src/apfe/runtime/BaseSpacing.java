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
package apfe.runtime;

import apfe.runtime.CharBuffer.Marker;
import java.util.List;

/**
 * Match (space | comment)*
 * @author gburdell
 */
public abstract class BaseSpacing extends Acceptor {
    /**
     * Create matcher for (spc | cmnt)*
     * @param spc space acceptor.
     * @param cmnt comment acceptor.
     */
    protected BaseSpacing(Acceptor spc, Acceptor cmnt) {
        m_spc = spc;
        m_cmnt = cmnt;
    }

    private final Acceptor  m_spc, m_cmnt;
    
    @Override
    protected boolean accepti() {
        // (Space / Comment)*
        PrioritizedChoice pc1 = new PrioritizedChoice(new PrioritizedChoice.Choices() {
            @Override
            public Acceptor getChoice(int ix) {
                Acceptor a = null;
                switch (ix) {
                    case 0:
                        a = m_spc.create();
                        break;
                    case 1:
                        a = m_cmnt.create();
                        break;
                }
                return a;
            }
        });
        Repetition r1 = new Repetition(pc1, Repetition.ERepeat.eZeroOrMore);
        boolean match = (null != (r1 = match(r1)));
        if (match && (0 < r1.sizeofAccepted())) {
            List<Acceptor> eles = r1.getAccepted();
            m_str = Util.toString(eles).toString();
        }
        return match;
    }
    
    private String  m_str;
    
    @Override
    public String toString() {
        return (null != m_str) ? m_str : "";
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
     * Memoize for all instances of Spacing.
     */
    private static Memoize stMemo = new Memoize();
}
