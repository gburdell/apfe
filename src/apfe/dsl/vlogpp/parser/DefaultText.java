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
import apfe.runtime.CharBuffer;
import apfe.runtime.CharClass;
import apfe.runtime.CharSeq;
import apfe.runtime.ICharClass;
import apfe.runtime.Memoize;
import apfe.runtime.NotPredicate;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;

/**
 *
 * @author gburdell
 */
public class DefaultText extends Acceptor {

    @Override
    protected boolean accepti() {
        /*
              DefaultText <- VString
              / '(' (!')' DefaultText) ')'
              / '{' (!'}' DefaultText) '}'
              / '[' (!']' DefaultText) ']'
              / (Space / SL_COMMENT)+ DefaultText
              / (NotChar4 . DefaultText)?         
        */
        PrioritizedChoice pc1 = new PrioritizedChoice(new PrioritizedChoice.Choices() {
            @Override
            public Acceptor getChoice(int ix) {
                Acceptor a = null;
                switch (ix) {
                    case 0:
                        a = new VString();
                        break;
                    case 1:
                        a = getMatch('(', ')');
                        break;
                    case 2:
                        a = getMatch('{', '}');
                        break;
                    case 3:
                        a = getMatch('[', ']');
                        break;
                    case 4: {
                        PrioritizedChoice pc2 = new PrioritizedChoice(
                                new Space(), new Comment());
                        Repetition r1 = new Repetition(pc2, Repetition.ERepeat.eOneOrMore);
                        a = new Sequence(r1, new DefaultText());
                        break;
                    }
                    case 5:
                        a = new Repetition(
                                new Sequence(new NotChar4(), new CharClass(ICharClass.IS_ANY),
                                        new DefaultText()), Repetition.ERepeat.eOptional);
                        break;
                }
                return a;
            }
        });
        boolean match = (null != (pc1 = match(pc1)));
        if (match) {
            m_arg = pc1;
            m_str = super.toString();
        }
        return match;
    }

    private PrioritizedChoice m_arg;
    private String m_str;

    @Override
    public String toString() {
        return m_str;
    }

    /**
     * A convenience method to generate alternatives.
     *
     * @param c1 leading character.
     * @param c2 trailing character.
     * @return Acceptor sequence to use in alternative.
     */
    private static Acceptor getMatch(char c1, char c2) {
        // c1 !c2 ActualArgument c2
        Sequence s1 = new Sequence(new CharSeq(c1), new NotPredicate(new CharSeq(c2)),
                new DefaultText(), new CharSeq(c2));
        return s1;
    }

    private Contents m_contents;

    @Override
    public Acceptor create() {
        return new DefaultText();
    }

    @Override
    protected void memoize(CharBuffer.Marker mark, CharBuffer.Marker endMark) {
        stMemo.add(mark, this, endMark);
    }

    @Override
    protected Memoize.Data hasMemoized(CharBuffer.Marker mark) {
        return stMemo.memoized(mark);
    }

    /**
     * Memoize for all instances of ActualArgument.
     */
    private static Memoize stMemo = new Memoize();
}
