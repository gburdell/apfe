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
    /**
     * Convenience class for expressing vertex and its owner graph.
     */
    public static class VG {
        public VG(Vertex v, Graph g) {
            m_v = v;
            m_g = g;
        }
        public final Vertex m_v;
        public final Graph  m_g;
    }
    
    protected Acceptor() {

    }

    /**
     * Create new instance of derived Acceptor.
     *
     * @return new instance.
     */
    public abstract Acceptor create();

    /**
     * Explore whether this Acceptor can consume prescribed sequence from
     * start.
     *
     * @param start root vertex and its owner.
     * @return subgraph if accepted else null.
     */
    public Graph accept(VG start) {
        m_parent = start;
        m_subgraph = new Graph(m_parent.m_v);
        //TODO: when to fold subgraph into parent???
        return acceptImpl();
    }
    
    public Graph accept(Vertex start, Graph g) {
        return accept(new VG(start, g));
    }

    protected abstract Graph acceptImpl();

    protected Token getToken() {
        return getSubgraphRoot().getState().getToken();
    }

    protected Vertex<State> getSubgraphRoot() {
        return getSubgraph().getRoot();
    }
    
    protected Graph getSubgraph() {
        return m_subgraph;
    }
    
    /**
     * The subgraph we are building with this acceptor.
     */
    private Graph m_subgraph;
    /**
     * The start vertex (which roots subgraph) and its parent/owner graph.
     */
    private VG    m_parent;
}
