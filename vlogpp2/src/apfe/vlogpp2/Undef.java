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
import apfe.runtime.CharSeq;
import apfe.runtime.Sequence;
import apfe.runtime.Util;

/**
 *
 * @author gburdell
 */
public class Undef extends Acceptor {

    @Override
    protected boolean accepti() {
        //Undef <- "`undef" Spacing Identifier
        Location loc = Location.getCurrent();
        Sequence s1 = new Sequence(new CharSeq("`undef"), new Spacing(),
                new Identifier());
        boolean match = (null != (s1 = match(s1)));
        if (match) {
            m_ident = Util.extractEleAsString(s1, 2);
            m_text = super.toString();
            //remove defn from just compilation unit
            final Helper helper = Helper.getTheOne();
            if (helper.getConditionalAllow()) {
                final String cuFname = helper.getFname();
                helper.getMacroDefns().undef(m_ident, cuFname, loc);
                helper.replace(super.getStartMark());
            }
        }
        return match;
    }
    private String m_text;
    private String m_ident;

    @Override
    public String toString() {
        return m_text;
    }

    @Override
    public Acceptor create() {
        return new Undef();
    }
}
