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
package apfe.vlogpp2;

import apfe.runtime.Acceptor;
import apfe.runtime.Marker;
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
public class ListOfFormalArguments extends Acceptor {

    @Override
    protected boolean accepti() {
        //ListOfFormalArguments <- FormalArgument (Spacing ',' FormalArgument)* Spacing
        FormalArgument f1 = new FormalArgument();
        boolean match = (null != (f1 = match(f1)));
        if (match) {
            m_fargs = new LinkedList<>();
            m_fargs.add(f1);
            Sequence s1 = new Sequence(new Spacing(), new CharSeq(','), new FormalArgument());
            Repetition r1 = new Repetition(s1, Repetition.ERepeat.eZeroOrMore);
            match &= (null != (r1 = match(r1)));
            assert match;
            Util.extractList(r1, 2, m_fargs);
            m_str = super.toString();
            match &= (new Spacing()).acceptTrue();
            assert match;
        }
        return match;
    }

    private List<FormalArgument> m_fargs;
    private String m_str;
    
    public List<FormalArgument> getFormalArgs() {
        return m_fargs;
    }

    @Override
    public String toString() {
        return m_str;
    }

    @Override
    public Acceptor create() {
        return new ListOfFormalArguments();
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
     * Memoize for all instances of ListOfFormalArguments.
     */
    private static Memoize stMemo = new Memoize();
}
