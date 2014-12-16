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

import apfe.dsl.vlogpp.Helper;
import static gblib.Util.abnormalExit;
import apfe.runtime.Acceptor;
import apfe.runtime.CharClass;
import apfe.runtime.CharSeq;
import apfe.runtime.ICharClass;
import apfe.runtime.Repetition;
import apfe.runtime.RestOfLine;
import apfe.runtime.Sequence;
import apfe.runtime.Util;

/**
 *
 * @author gburdell
 */
public class Line extends Acceptor {

    @Override
    protected boolean accepti() {
        //Line <- "`line" Spacing [0-9]+ Spacing VString Spacing [0-2]
        CharClass c1 = new CharClass(ICharClass.IS_DIGIT);
        Repetition r1 = new Repetition(c1, Repetition.ERepeat.eOneOrMore);
        Sequence s1 = new Sequence(new CharSeq("`line"), new Spacing(), r1,
                new Spacing(), new VString(), new Spacing(),
                new CharClass(CharClass.matchOneOf("012")));
        boolean match = (null != (s1 = match(s1)));
        match &= (new RestOfLine()).acceptTrue();
        if (match && Helper.getTheOne().getConditionalAllow()) {
            m_fname = Util.extractEleAsString(s1, 4);
            m_lnum = Integer.parseInt(Util.extractEleAsString(s1, 2));
            m_type = Integer.parseInt(Util.extractEleAsString(s1, 6));
            switch (m_type) {
                case 1:
                    if (stMaxInclNestCnt < stInclNestCnt++) {
                        Helper.error("VPP-INCL-3", stMaxInclNestCnt);
                        abnormalExit(new Exception("Unexpected.  Very bad."));
                    }
                    break;
                case 2:
                    stInclNestCnt = (0 < stInclNestCnt) ? stInclNestCnt - 1 : 0;
                    break;
            }
            m_text = super.toString();
            Helper.getTheOne().reset(m_fname, m_lnum);
        }
        return match;
    }

    private String m_text, m_fname;
    private int m_lnum, m_type;

    @Override
    public Acceptor create() {
        return new Line();
    }

    /**
     * Track include file nesting count.
     */
    private static int stInclNestCnt = 0;

    public static int stMaxInclNestCnt = 32;
}
