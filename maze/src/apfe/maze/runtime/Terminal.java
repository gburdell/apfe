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

import apfe.maze.runtime.Graph.V;

/**
 * A single token.
 *
 * @author gburdell
 */
public class Terminal extends Acceptor {

    /**
     * Accept a single token.
     *
     * @param tokCode of single token to accept.
     */
    public Terminal(int tokCode) {
        m_tokCode = tokCode;
    }

    @Override
    public Acceptor create() {
        return new Terminal(m_tokCode);
    }

    /**
     * Try to accept terminal. If so, add edge to subgraph with leaf vertex
     * updated to scanner next position (unless src already at EOF).
     *
     * @return true if accepted else false.
     */
    @Override
    protected boolean acceptImpl() {
        final boolean match = (m_tokCode == getToken().getCode());
        V src = getSubgraphRoot();
        if ((m_tokCode == getToken().getCode())
                && !src.getData().getToken().isEOF()) { //dont repeat EOF
            m_matched = getToken();
            V dest = src.getData().getNextVertex();
            getSubgraph().addEdge(src, dest, this);
        }
        return match;
    }

    @Override
    public String toString() {
        return m_matched + ":" + m_tokCode;
    }

    public Token getMatched() {
        return m_matched;
    }

    public int getTokCode() {
        return m_tokCode;
    }

    private final int m_tokCode;
    private Token m_matched;

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();

}
