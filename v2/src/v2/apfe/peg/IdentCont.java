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

import apfe.runtime.Acceptor;
import apfe.runtime.Marker;
import apfe.runtime.CharClass;
import apfe.runtime.ICharClass;
import apfe.runtime.Memoize;
import apfe.runtime.PrioritizedChoice;

public class IdentCont extends Acceptor {

    public IdentCont() {
    }

    @Override
    protected boolean accepti() {
        // IdentStart / [0-9]
        PrioritizedChoice acc = new PrioritizedChoice(new Alts());
        boolean match = (null != (acc = match(acc)));
        if (match) {
            m_literal = acc.getAccepted().toString();
        }
        return match;
    }

    private class Alts implements PrioritizedChoice.Choices {
        @Override
        public Acceptor getChoice(int ix) {
            Acceptor acc = null;
            switch (ix) {
                case 0:
                    acc = new IdentStart();
                    break;
                case 1:
                    acc = new CharClass(ICharClass.IS_DIGIT);
                    break;
            }
            return acc;
        }        
    }
    
    @Override
    public String toString() {
        return m_literal;
    }
    
    private String m_literal;

    @Override
    public Acceptor create() {
        return new IdentCont();
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
     * Memoize for all instances of IdentCont.
     */
    private static Memoize stMemo = new Memoize();
}
