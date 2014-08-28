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
 *
 * @author gburdell
 */
public abstract class Acceptor {
    protected Acceptor() {

    }

    /**
     * Create new instance of derived Acceptor.
     *
     * @return new instance.
     */
    public abstract Acceptor create();

    /**
     * Explore whether this Acceptor can consume prescribed sequence from start.
     *
     * @param start root vertex.
     * @return subgraph if accepted else null.
     */
    public Graph accept(Vertex<State> start) {
        m_subgraph = new Graph(start);
        final boolean match = acceptImpl();
        return match ? m_subgraph : null;
    }

    protected abstract boolean acceptImpl();

    protected Token getToken() {
        return getSubgraphRoot().getState().getToken();
    }

    protected Vertex<State> getSubgraphRoot() {
        return getSubgraph().getRoot();
    }

    protected Graph getSubgraph() {
        return m_subgraph;
    }

    @Override
    public String toString() {
        return Class.class.getSimpleName();
    }

    /**
     * The subgraph we are building with this acceptor.
     */
    private Graph m_subgraph;
}
