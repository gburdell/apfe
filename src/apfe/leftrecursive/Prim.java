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
package apfe.leftrecursive;

import apfe.runtime.Acceptor;
import apfe.runtime.CharSeq;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Sequence;

public class Prim extends Acceptor {

    public Prim() {
    }

    @Override
    protected boolean accepti() {
        PrioritizedChoice pc1 = new PrioritizedChoice(new PrioritizedChoice.Choices() {
            @Override
            public Acceptor getChoice(int ix) {
                Acceptor a = null;
                switch (ix) {
                    case 0:
                        a = new Sequence(new CharSeq('('), new Term(), new CharSeq(')'));
                        break;
                    /**/
                    case 1:
                        a = new LrInteger();
                        break;
                }
                return a;
            }
        });
        boolean match = (null != (pc1 = match(pc1)));
        if (match) {
            m_val = pc1.getAccepted();
        }
        return match;
    }
    private Acceptor m_val;

    @Override
    public Acceptor create() {
        return new Prim();
    }

    @Override
    public String toString() {
        return m_val.toString();
    }

    @Override
    public String getNonTermID() {
        return getClass().getSimpleName();
    }
}
