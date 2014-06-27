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

import apfe.dsl.vlogpp.Main;
import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer;
import apfe.runtime.CharSeq;
import apfe.runtime.Memoize;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;

/**
 *
 * @author gburdell
 */
public class TicDefine extends Acceptor {

    @Override
    protected boolean accepti() {
        //TicDefine <- "`define" Spacing TextMacroName (MacroText)?
        Repetition r1 = new Repetition(new MacroText(), Repetition.ERepeat.eOptional);
        Sequence s1 = new Sequence(new CharSeq("`define"), new Spacing(),
                new TextMacroName(), r1);
        boolean match = (null != (s1 = match(s1)));
        if (match) {
            m_name = Util.extractEleAsString(s1, 2);
            MacroText mtext = null;
            r1 = Util.extractEle(s1, 3);
            if (0 < r1.sizeofAccepted()) {
                mtext = r1.getOnlyAccepted();
                m_text = r1.getOnlyAccepted().toString();
            }
            addDefn(mtext);
        }
        return match;
    }

    private void addDefn(MacroText mtext) {
        Main mn = Main.getTheOne();
        if (mn.getConditionalAllow()) {
            //TODO: MacroText needs to retain structure/object
            if (null == mtext) {
                mn.getMacroDefns().add(m_name, null);
            } else {
                //add one with more
            }
        }
    }
    private String m_name;
    private String m_text;

    @Override
    public Acceptor create() {
        return new TicDefine();
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
     * Memoize for all instances of TicDefine.
     */
    private static Memoize stMemo = new Memoize();
}
