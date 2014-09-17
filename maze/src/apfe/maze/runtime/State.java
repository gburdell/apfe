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

import apfe.maze.runtime.graph.Vertex;

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

    public Token getToken() {
        return m_lex.get(getPos());
    }

    public int getPos() {
        return m_pos;
    }

    public State getNext() {
        return new State(m_lex, m_pos + 1);
    }

    public Vertex<State, Acceptor> getNextVertex() {
        return new Vertex<>(getNext());
    }

    @Override
    public boolean equals(Object obj) {
        boolean eq = (obj != null)
                && (((State) obj).getPos() == getPos());
        return eq;
    }

    public final Scanner m_lex;
    public final int m_pos;
}
