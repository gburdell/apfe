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
import apfe.runtime.CharSeq;
import apfe.runtime.Memoize;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class ListOfActualArguments extends Acceptor {

    @Override
    protected boolean accepti() {
        //ListOfActualArguments <- ActualArgument (',' ActualArgument)*
        ActualArgument a1 = new ActualArgument();
        boolean match = (null != (a1 = match(a1)));
        if (match) {
            m_args = new LinkedList<>();
            m_args.add(a1.toString());
            Sequence s1 = new Sequence(new CharSeq(','), new ActualArgument());
            Repetition r1 = new Repetition(s1, Repetition.ERepeat.eZeroOrMore);
            match &= (null != (r1 = match(r1)));
            if (match && (0 < r1.sizeofAccepted())) {
                List<ActualArgument> laa = new LinkedList<>();
                Util.extractList(r1, 1, laa);
                for (ActualArgument aa : laa) {
                    m_args.add(aa.toString());
                }
            }
        }
        return match;
    }

    private List<String> m_args;
    
    /**
     * Get actual arguments.
     * @return null or list of arguments.
     */
    public List<String> getArgs() {
        return m_args;
    }

    @Override
    public Acceptor create() {
        return new ListOfActualArguments();
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
     * Memoize for all instances of ListOfActualArguments.
     */
    private static Memoize stMemo = new Memoize();
}
