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
import apfe.runtime.CharBuffer;
import apfe.runtime.CharClass;
import apfe.runtime.CharSeq;
import apfe.runtime.EndOfLine;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;

/**
 *
 * @author gburdell
 */
public class MacroText extends Acceptor {

    @Override
    protected boolean accepti() {
        CharBuffer cbuf = Helper.getBuf();
        StringBuilder sb = new StringBuilder();
        boolean ok = true;
        while (ok) {
            Repetition a = new Repetition(new CharClass(CharClass.matchOneOf(" \t")),
                    Repetition.ERepeat.eZeroOrMore);
            assert (null != (a = match(a)));
            if (0 < a.sizeofAccepted()) {
                sb.append(a.toString());
            }
            if (cbuf.la() == CharBuffer.NL) {
                ok = false;
            } else if ((new Comment.SL_COMMENT()).acceptTrue()) {
                ok = false;
            } else if ((new Comment.ML_COMMENT()).acceptTrue()) {
                ; //stay here
            } else if ((new Sequence(new CharSeq("\\"), new EndOfLine())).acceptTrue()) {
                sb.append(CharBuffer.NL);
                ;//stay here
            } else {
                sb.append(cbuf.accept());
            }
        }
        m_text = sb.toString().replace('\t', ' ');
        (new EndOfLine()).acceptTrue();
        return true;
    }

    private String m_text;

    @Override
    public String toString() {
        return m_text;
    }

    @Override
    public Acceptor create() {
        return new MacroText();
    }

}
