/*
 * The MIT License
 *
 * Copyright 2016 gburdell.
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
package laol.ast;

import apfe.runtime.Acceptor;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Sequence;
import apfe.runtime.Util;
import static apfe.runtime.Util.extractEle;
import gblib.Pair;

/**
 *
 * @author gburdell
 */
public class Number {
    public Number(final apfe.laol.generated.Number num) {
        final PrioritizedChoice pc = extractEle(num.getBaseAccepted(), 0);
        final Acceptor tok =  pc.getAccepted();
        switch (pc.whichAccepted()) {
            case 0: 
                m_val = new Based(tok);
                break;
            case 1:
                m_val = new Float(tok);
                break;
            default:
                m_val = new Integer(tok);
        }
    }
    
    @Override
    public String toString() {
        return m_val.toString();
    }
    
    private final Val   m_val;
    
    public static abstract class Val {
        protected Val(final Acceptor tok) {
            m_tok = tok;
        }
        
        @Override
        public String toString() {
            return m_tok.toString();
        }
        
        final Acceptor m_tok;
    }
    
    public static class Based extends Val {
        private Based(final Acceptor tok) {
            super(tok);
            final Sequence seq = tok.getBaseAccepted();
            Pair<Boolean, java.lang.Integer> v = Util.asInt(seq, 0);
            m_size = (v.v1) ? v.v2 : -1;
            String s = seq.getText(3);
            m_base = Character.toUpperCase(s.charAt(0));
            m_val = s.substring(1);
        }
        
        private final int m_size;
        private final char m_base;
        private final String m_val;
    }
    
    public static class Float extends Val {
        private Float(final Acceptor tok) {
            super(tok);
            
        }
    }
    
    public static class Integer extends Val {
        private Integer(final Acceptor tok) {
            super(tok);
            
        }
    }
}
