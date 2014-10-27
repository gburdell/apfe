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
import apfe.runtime.CharClass;
import apfe.runtime.CharSeq;
import apfe.runtime.ICharClass;
import apfe.runtime.Memoize;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;

/**
 *
 * @author gburdell
 */
public class TimeValue extends Acceptor {

    @Override
    protected boolean accepti() {
        //TimeValue <- [0-9]+ Spacing ("s" / "ms" / "us" / "ns" / "ps" / "fs")
        CharClass c1 = new CharClass(ICharClass.IS_DIGIT);
        Repetition r1 = new Repetition(c1, Repetition.ERepeat.eOneOrMore);
        PrioritizedChoice p1 = new PrioritizedChoice(new CharSeq("s"),
                new CharSeq("ms"), new CharSeq("us"), new CharSeq("ns"), new CharSeq("ps"),
                new CharSeq("fs"));
        Sequence s1 = new Sequence(r1, new Spacing(), p1);
        boolean match = (null != (s1 = match(s1)));
        if (match) {
            m_num = Integer.parseInt(Util.extractEleAsString(s1, 0));
            m_units = Util.extractEleAsString(s1, 2);
            m_text = super.toString();
        }
        return match;
    }

    private int m_num;
    private String m_units;
    private String m_text;

    @Override
    public String toString() {
        return m_text;
    }

    @Override
    public Acceptor create() {
        return new TimeValue();
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
     * Memoize for all instances of TimeValue.
     */
    private static Memoize stMemo = new Memoize();
}
