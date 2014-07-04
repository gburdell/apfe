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

import java.util.Stack;


/**
 * Encapsulate parser state so more can pass (entire state) as single object.
 * @author gburdell
 */
public class State {
    public static State create(CharBuffer buf) {
        ParseError.reset();
        Memoize.reset();
        stTheOne = new State(buf);
        return getTheOne();
    }
    
    public static State getTheOne() {
        return stTheOne;
    }
    
    private static State stTheOne;
    
    private State(CharBuffer buf) {
        m_buf = buf;
    }
    
    public CharBuffer getBuf() {
        return m_buf;
    }
    
    public CharBuffer.Marker getCurrentMark() {
        return getBuf().mark();
    }
    
    public boolean isEOF() {
        return (CharBuffer.EOF == peek());
    }
    
    public int incrPredicate() {
        return ++m_isPredicate;
    }
    
    public int decrPredicate() {
        m_isPredicate--;
        assert 0 <= m_isPredicate;
        return m_isPredicate;
    }
    
    public boolean inPredicate() {
        return 0 < m_isPredicate;
    }
    
    public char peek() {
        return peek(0);
    }
    
    public char peek(int i) {
        return la(i);
    }
    
    public char la(int i) {
        return getBuf().la(i);
    }
    
    /**
     * Push non-terminal to enable indirect left recursion detection.
     * @param nt non-terminal ID.
     */
    public void pushNonTermID(String nt) {
        m_nonTermStk.push(nt);
    }
    
    public String popNonTermID() {
        return m_nonTermStk.pop();
    }
       
    private CharBuffer m_buf;
    /**
     * Count (incr and decr) predicate depth.
     */
    private int     m_isPredicate = 0;
    
    /**
     * Track non-terminal calls to detect indirect left-recursion.
     * TODO: I think we can just put a static boolean member in
     * each non-terminal to track whether we have passed through this
     * non-term already.
     * For now, just stick with the stack.
     */
    private Stack<String>   m_nonTermStk = new Stack<>();
    
    public int getStackDepth() {
        return m_nonTermStk.size();
    }
    
    public void updateMemoizeHit(boolean matched) {
        m_memoizeTryCnt++;
        if (matched) {
            m_memoizeHitCnt++;
        }
    }
    
    /**
     * Get memoize stats.
     * @return 2 elements: [0]: hit count; [1]: try count.
     */
    public long[] getMemoizeStats() {
        return new long[]{m_memoizeHitCnt,m_memoizeTryCnt};
    }
    
    /**
     * Track memoize attempts.
     */
    private long m_memoizeTryCnt = 0;
    /**
     * Track memoize hit count.
     */
    private long m_memoizeHitCnt = 0;
}
