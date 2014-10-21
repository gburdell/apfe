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
import apfe.runtime.EndOfFile;
import apfe.runtime.NotPredicate;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.State;

/**
 *
 * @author gburdell
 */
public class AnyChar extends Acceptor {

    @Override
    protected boolean accepti() {
        //AnyChar <- !(Space / Comment / EOF) .
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
                    case 2:
                        a = new EndOfFile();
                        break;
                }
                return a;
            }
        });
        NotPredicate np1 = new NotPredicate(pc);
        boolean match = np1.acceptTrue();
        if (match) {
            m_ch = State.getTheOne().getBuf().accept();
        }
        return match;
    }

    private char m_ch = 0;

    public char asChar() {
        return m_ch;
    }

    @Override
    public String toString() {
        return (0 != m_ch) ? Character.toString(m_ch) : "";
    }

    @Override
    public Acceptor create() {
        return new AnyChar();
    }

    //Note: Dont memoize.  Too expensive.
}
