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
import apfe.runtime.PrioritizedChoice;

public class VIdent extends Acceptor {

    public VIdent() {
    }

    @Override
    protected boolean accepti() {
        // VIdent <- K_CELL / K_LIBRARY / IDENT
        PrioritizedChoice pc1 = new PrioritizedChoice(new PrioritizedChoice.Choices() {

            @Override
            public Acceptor getChoice(int ix) {
                Acceptor a = null;
                switch(ix) {
                    case 0:
                        a = new Operator(Operator.EOp.K_CELL);
                        break;
                    case 1:
                        a = new Operator(Operator.EOp.K_LIBRARY);
                        break;
                    case 2:
                        a = new Ident();
                        break;
                }
                return a; 
            }
        });
        boolean match = (null != (pc1 = match(pc1)));
        if (match) {
            //todo
        }
        return match;
    }

    @Override
    public Acceptor create() {
        return new VIdent();
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
     * Memoize for all instances of VIdent.
     */
    private static Memoize stMemo = new Memoize();
}
