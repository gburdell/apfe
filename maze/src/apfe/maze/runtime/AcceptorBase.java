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
    public abstract boolean accept(State state);
    
    /**
     * Encapsulate start position of Scanner/Lexer.
     */
    public static class State {
        /**
         * Capture (start) lexer position.
         * @param lex Scanner/lexer.
         * @param pos start position.
         */
        public State(ScannerBase lex, int pos) {
            m_lex = lex;
            m_pos = pos;
        }
        public final ScannerBase    m_lex;
        public final int            m_pos;
    }
}
