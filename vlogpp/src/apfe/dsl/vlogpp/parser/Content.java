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

/**
 *
 * @author gburdell
 */
public class Content extends Acceptor {

    @Override
    protected boolean accepti() {
        /* Content <- TicInclude
         / TicDefine
         / TicConditional
         / TicReserved
         / TicMacroUsage
         / AnyChar
         */
        PrioritizedChoice pc = new PrioritizedChoice(new PrioritizedChoice.Choices() {
            @Override
            public Acceptor getChoice(int ix) {
                Acceptor a = null;
                switch (ix) {
                    case 0:
                        a = new TicInclude();
                        break;
                    case 1:
                        a = new TicDefine();
                        break;
                    case 2:
                        a = new TicConditional();
                        break;
                    case 3:
                        a = new TicReserved();
                        break;
                    case 4:
                        a = new TicMacroUsage();
                        break;
                    case 5:
                        a = new AnyChar();
                        break;
                };
                return a;
            }
        });
        boolean match = (null != (pc = match(pc)));
        if (match) {
            m_contents = pc.toString();
        }
        return match;
    }

    private String m_contents;

    @Override
    public Acceptor create() {
        return new Content();
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
     * Memoize for all instances of Content.
     */
    private static Memoize stMemo = new Memoize();
}
