/*
 * The MIT License
 *
 * Copyright 2015 gburdell.
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
import apfe.runtime.CharBufState;
import apfe.runtime.CharBuffer;
import static apfe.runtime.CharBuffer.EOF;
import apfe.runtime.CharSeq;
import apfe.runtime.Marker;
import apfe.runtime.PrioritizedChoice;

/**
 * Handle `protected clause
 *
 * @author gburdell
 */
public class Protected extends Acceptor {

    public Protected() {
    }

    @Override
    public Acceptor create() {
        return new Protected();
    }

    @Override
    protected boolean accepti() {
        final Marker start = getCurrentMark();
        Acceptor acc = new PrioritizedChoice(new CharSeq("`protected"), new CharSeq("`protect"));
        boolean match = (null != match(acc));
        if (match) {
            final CharBuffer cbuf = CharBufState.asMe().getBuf();
            while (true) {
                if ('`' == cbuf.la(0)) {
                    match = (new PrioritizedChoice(new CharSeq("`endprotected"), new CharSeq("`endprotect"))).acceptTrue();
                    if (match) {
                        break; //while
                    }
                }
                if (EOF == cbuf.accept()) {
                    break;
                }
            }
        }
        if (match) {
            m_text = super.toString();
        }
        return match;
    }

    @Override
    public String toString() {
        return m_text;
    }

    private String m_text;

}
