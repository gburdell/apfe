/*
 * The MIT License
 *
 * Copyright 2013 gburdell.
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
package apfe.runtime;


/**
 * Accepts character sequence.
 * @author gburdell
 */
public class CharSeq extends Acceptor {
    public CharSeq(char c) {
        m_expect = new String(new char[]{c});
    }

    public CharSeq(String s) {
        m_expect = s;
    }

    @Override
    public Acceptor create() {
        return new CharSeq(m_expect);
    }

    @Override
    public String toString() {
        return m_expect;
    }
    
    private final String m_expect;
    
    @Override
    protected boolean accepti() {
        CharBuffer buf = CharBufState.asMe().getBuf();
        boolean match = false;
        char c;
        StringBuilder acc = new StringBuilder(m_expect.length());
        for (int i = 0; i < m_expect.length(); i++) {
            c = buf.la();
            acc.append(Char.toString(c));
            match = (m_expect.charAt(i) == c);
            if (match) {
                buf.accept();
            } else {
                break;
            }
        }
        if (!match) {
            ParseError.push(acc.toString(), "'"+m_expect+"'");
        }
        return match;
    }
    
}
