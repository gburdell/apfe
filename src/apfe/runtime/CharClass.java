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

import apfe.peg.Char;

/**
 * @author gburdell
 */
public class CharClass extends Acceptor {
    public CharClass(ICharClass ... eles) {
        m_eles = eles;
    }

    public static ICharClass matchOneOf(String oneOf) {
        return new CharMatch(oneOf);
    }
    
    /**
     * Convenience class to match any one of characters in a string.
     * Useful for matching in class of unrelated chars: e.g. ['-''+']
     */
    private static class CharMatch implements ICharClass {
        public CharMatch(String oneOf) {
            m_oneOf = oneOf;
        }
        private final String    m_oneOf;

        @Override
        public boolean inClass(char c) {
            return 0 <= m_oneOf.indexOf(c);
        }

        @Override
        public String getExpected() {
            return "[" + m_oneOf + "]";
        }
        
        
        
    }
    
    private final ICharClass m_eles[];
       
    @Override
    public Acceptor create() {
        return new CharClass(m_eles);
    }

    @Override
    public String toString() {
        return String.valueOf(m_ch);
    }
    
    private char m_ch;
    
    @Override
    protected boolean accepti() {
        CharBuffer buf = State.getTheOne().getBuf();
        boolean match = false;
        char c = buf.la();
        for (ICharClass cc : m_eles) {
            if (cc.inClass(c)) {
                match = true;
                m_ch = c;
                break;
            }
        }
        if (match) {
            buf.accept();
        } else {
            //Build up set
            StringBuilder s = new StringBuilder("[");
            for (ICharClass cc : m_eles) {
                final String m = cc.getExpected();
                final int n = m.length()-1;
                assert ('['==m.charAt(0) && ']'==m.charAt(n));
                s.append(m.substring(1, n));
            }
            s.append(']');
            ParseError.push(c, s.toString());
        }
        return match;
    }
    
}
