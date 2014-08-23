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

package apfe.maze.runtime;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gburdell
 */
public abstract class AcceptorBase {
    protected AcceptorBase() {
        
    }
    
    /**
     * Create new instance of derived Acceptor.
     * @return new instance.
     */
    public abstract AcceptorBase create();
    
    /**
     * Explore whether this Acceptor can consume prescribed sequence
     * from Scanner/lexer state.
     * @param state Scanner start.
     * @return true if can accept prescribed sequence.
     */
    public boolean accept(State state) {
        m_state = state;
        return acceptImpl();
    }
    
    protected abstract boolean acceptImpl();
    
    public Iterable<AcceptorBase> getAccepted() {
        return (null != m_accepted) ? m_accepted : stEmpty;
    }
    
    /**
     * Encapsulate start position of Scanner/Lexer.
     */
    public static class State {
        /**
         * Capture (start) lexer position.
         * @param lex Scanner/lexer.
         * @param pos start position.
         * @param parent parent for graph.
         */
        public State(ScannerBase lex, int pos, AcceptorBase parent) {
            m_lex = lex;
            m_pos = pos;
            m_parent = parent;
        }
        
        public State(ScannerBase lex, int pos) {
            this(lex, pos, null);
        }
        
        public final ScannerBase    m_lex;
        public final int            m_pos;
        public final AcceptorBase   m_parent;
    }
    
    protected TokenBase getToken() {
        return m_state.m_lex.get(getPos());
    }
    
    protected int getPos() {
        return m_state.m_pos;
    }
    
    protected void addAccepted(AcceptorBase acc) {
        if (null == m_accepted) {
            m_accepted = new LinkedList<>();
        }
        m_accepted.add(acc);
    }
    
    private State   m_state;
    
    private List<AcceptorBase>  m_accepted;
    
    private static final Iterable<AcceptorBase> stEmpty = new Util.EmptyCollection<>();
}
