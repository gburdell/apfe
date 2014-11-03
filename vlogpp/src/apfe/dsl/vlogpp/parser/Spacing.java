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
import apfe.runtime.Memoize;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Repetition;
import apfe.runtime.Space;

/**
 *
 * @author gburdell
 */
public class Spacing extends Acceptor {
    public Spacing() {
        this(true);
    }
    
    public Spacing(boolean matchOnEmpty) {
        m_matchOnEmpty = matchOnEmpty;
    }
    
    private boolean m_matchOnEmpty;
    
    @Override
    protected boolean accepti() {
        //Spacing <- (Space / Comment)*
        PrioritizedChoice pc = new PrioritizedChoice(new PrioritizedChoice.Choices() {
            @Override
            public Acceptor getChoice(int ix) {
                Acceptor a = null;
                switch (ix) {
                    case 0:
                        a = new Space();
                        break;
                    case 1:
                        a = new Comment();
                        break;
                };
                return a;
            }
        });
        Repetition r1 = new Repetition(pc, Repetition.ERepeat.eZeroOrMore);
        boolean match = (null != (r1 = match(r1)));
        match &= !(!m_matchOnEmpty && (0 == r1.sizeofAccepted()));
        if (match) {
            m_spacing = (0 < r1.sizeofAccepted()) ? r1.getAccepted().toString() : "";
        }
        return match;
    }

    private String m_spacing;

    @Override
    public String toString() {
        return m_spacing;
    }

    
    @Override
    public Acceptor create() {
        return new Spacing(m_matchOnEmpty);
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
