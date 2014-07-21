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
import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer.Marker;
import apfe.runtime.Memoize;
import apfe.runtime.NotPredicate;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;

public class Literal extends Acceptor implements GenJava.IGen {

    public Literal() {
    }

    @Override
    public GenJava genJava(GenJava j) {
        return j.template("new CharSeq(@1@)", toString());
    }

    @Override
    public String toString() {
        return m_literal;
    }

    @Override
    protected boolean accepti() {
        /* [’] ![’] '\\'? Char [’] Spacing
         / ["] (!["] '\\'? Char)+ ["] Spacing
         */
        PrioritizedChoice.Choices alts = new PrioritizedChoice.Choices() {
            @Override
            public Acceptor getChoice(int ix) {
                Acceptor acc = null;
                switch (ix) {
                    case 0: {
                        acc = new Sequence(new Char('\''), new NotPredicate(new Char('\'')),
                                new Repetition(new Char('\\'), Repetition.ERepeat.eOptional),
                                new Char(), new Char('\''));
                    }
                    break;
                    case 1: {
                        Sequence e2 = new Sequence(new NotPredicate(new Char('"')),
                                new Repetition(new Char('\\'), Repetition.ERepeat.eOptional),
                                new Char());
                        acc = new Sequence(new Char('"'), new Repetition(e2, Repetition.ERepeat.eOneOrMore),
                                new Char('"'));

                    }
                    break;
                }
                return acc;
            }
        };
        PrioritizedChoice e4 = new PrioritizedChoice(alts);
        boolean match = e4.acceptTrue();
        if (match) {
            m_literal = super.toString();
            match &= (new Spacing()).acceptTrue();
        }
        return match;
    }

    private String m_literal;

    @Override
    public Acceptor create() {
        return new Literal();
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
     * Memoize for all instances of Literal.
     */
    private static final Memoize stMemo = new Memoize();
}
