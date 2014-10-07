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
 * Encapsulate start position of Scanner/Lexer.
 */
public class State {

    /**
     * Capture (start) lexer position.
     *
     * @param lex Scanner/lexer.
     * @param pos start position.
     */
    public State(Scanner lex, int pos) {
        m_lex = lex;
        m_pos = pos;
    }

    public State(State r) {
        this(r.m_lex, r.m_pos);
    }
    
    @Override
    public State clone() {
        return new State(this);
    }
    
    @Override
    public String toString() {
        return "(" + m_pos + ")";
    }

    public Token peek() {
        return m_lex.get(getPos());
    }

    public int getPos() {
        return m_pos;
    }

    /**
     * Advance to next token (but not past lastPos).
     * @return current token (before advance).
     */
    public Token advance() {
        Token curr = peek();
        if (getPos() < m_lex.size()) {
            m_pos++;
        }
        return curr;
    }
    
    public boolean isEOF() {
        return peek().isEOF();
    }

    @Override
    public boolean equals(Object obj) {
        assert (obj instanceof State);
        boolean eq = (((State) obj).getPos() == getPos());
        return eq;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public final Scanner m_lex;
    public int m_pos;
}
