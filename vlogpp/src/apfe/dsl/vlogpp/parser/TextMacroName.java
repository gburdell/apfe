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
import apfe.runtime.CharSeq;
import apfe.runtime.Memoize;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;

/**
 *
 * @author gburdell
 */
public class TextMacroName extends Acceptor {

    @Override
    protected boolean accepti() {
        //#NOTE: LRM says no space between Identifier and '(' (if parameters)
        //TextMacroName <- Identifier ('(' ListOfFormalArguments ')')?
        Identifier id1 = new Identifier();
        boolean match = (null != (id1 = match(id1)));
        if (match) {
            Sequence s1 = new Sequence(new CharSeq('('),
                    new ListOfFormalArguments(), new CharSeq(')'));
            Repetition r1 = new Repetition(s1, Repetition.ERepeat.eOptional);
            match &= (null != (r1 = match(r1)));
            if (match) {
                m_id = id1.toString();
                if (0 < r1.sizeofAccepted()) {
                    s1 = r1.getOnlyAccepted();
                    m_args = Util.extractEle(s1, 1);
                }
            }
            m_text = super.toString();
        }
        return match;
    }

    private String m_id;
    private ListOfFormalArguments m_args;
    private String m_text;

    public String getId() {
        return m_id;
    }

    public ListOfFormalArguments getFormalArgs() {
        return m_args;
    }

    @Override
    public String toString() {
        return m_text;
    }

    @Override
    public Acceptor create() {
        return new TextMacroName();
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
     * Memoize for all instances of TextMacroName.
     */
    private static Memoize stMemo = new Memoize();
}
