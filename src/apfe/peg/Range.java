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
package apfe.peg;

import apfe.peg.generate.GenJava;
import apfe.peg.generate.Template;
import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer.Marker;
import apfe.runtime.Memoize;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Sequence;

public class Range extends Acceptor implements GenJava.IGen {
    public Range() {
    }

    @Override
    public GenJava genJava(GenJava j) {
        if (1 < m_ch.length) {
            j.template("CharClass.matchRange('@1@','@2@')", 
                    m_ch[0].getChar(), m_ch[1].getChar());
        } else {
            j.template("new CharSeq('@1@')",m_ch[0].getChar());
        }
        return j;
    }

     @Override
    protected boolean accepti() {
        // Range <- Char '-' Char / Char
        PrioritizedChoice pc1 = new PrioritizedChoice(new PrioritizedChoice.Choices() {
            @Override
            public Acceptor getChoice(int ix) {
                Acceptor acc = null;
                switch (ix) {
                    case 0: {
                        Sequence s1 = new Sequence(new Char(), new Char('-'), new Char());
                        acc = s1;
                    }
                    break;
                    case 1:
                        acc = new Char();
                        break;
                    default:

                }
                return acc;
            }
        });
        boolean match = (null != (pc1 = match(pc1)));
        if (match) {
            if (0 == pc1.whichAccepted()) {
                Sequence s1 = (Sequence)pc1.getAccepted();
                Acceptor a1[] = s1.getAccepted();
                m_ch = new Char[]{(Char)a1[0], (Char)a1[2]};
            } else {
                m_ch = new Char[]{(Char)pc1.getAccepted()};
            }
        }
        return match;
    }
    
    private Char m_ch[];

    public Char[] getRange() {
        return m_ch;
    }
    
    @Override
    public String toString() {
        if (null == m_ch) {
            return null;
        }
        StringBuilder bld = new StringBuilder(m_ch[0].toString());
        if (1 < m_ch.length) {
            bld.append('-').append(m_ch[1]);
        }
        return bld.toString();
    }

    @Override
    public Acceptor create() {
        return new Range();
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
     * Memoize for all instances of Range.
     */
    private static Memoize stMemo = new Memoize();
}
