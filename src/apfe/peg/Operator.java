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
package apfe.peg;

import apfe.runtime.Acceptor;
import static apfe.runtime.Acceptor.match;
import apfe.runtime.CharBuffer.Marker;
import apfe.runtime.CharSeq;
import apfe.runtime.Memoize;
import apfe.runtime.Sequence;
import apfe.runtime.Util;

/**
 * @author gburdell
 */
public class Operator extends Acceptor {
    public static enum EOp {
        LEFTARROW("<-"), SLASH('/'), AND('&'), NOT('!'), 
        QUESTION('?'), STAR('*'), PLUS('+'),
        OPEN('('), CLOSE(')'), DOT('.'),
        LCURLY2("{{"), RCURLY2("}}"),
        EQ('='), EXT_OP("<<");
        
        private EOp(char c) {
            m_cs = new CharSeq(c);
        }
        private EOp(String s) {
            m_cs = new CharSeq(s);
        }
        private CharSeq getCharSeq() {
            return m_cs;
        }
        @Override
        public String toString() {
            return m_cs.toString();
        }
        private CharSeq m_cs;
    }

    public Operator(EOp op) {
        m_op = op;
    }

    private final EOp   m_op;
    
    public EOp getOp() {
        return m_op;
    }
    
    @Override
    public String toString() {
        return Util.toString(m_op.getCharSeq()).toString();
    }
    
    @Override
    protected boolean accepti() {
        Sequence s1 = new Sequence(m_op.getCharSeq(), new Spacing());
        boolean match = (null != (s1 = match(s1)));
        return match;
    }

    @Override
    public Acceptor create() {
        return new Operator(m_op);
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
     * Memoize for all instances of Operator.
     */
    private static Memoize stMemo = new Memoize();
}
