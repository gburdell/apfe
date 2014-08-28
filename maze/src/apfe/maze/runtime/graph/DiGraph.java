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
package apfe.maze.runtime.graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A directed graph with time-efficient leaf detection.
 *
 * @author gburdell
 * @param <V> vertex data type.
 * @param <E> edge data type.
 */
public class DiGraph<V, E> {

    public DiGraph(Vertex<V> root) {
        m_root = root;
    }

    public DiGraph(V root) {
        this(new Vertex(root));
    }

    public Vertex<V> getRoot() {
        return m_root;
    }

    public int addLeafs(Iterable<Vertex<V>> leafs) {
        if (null != leafs) {
            for (Vertex leaf : leafs) {
                if (null == m_leafs) {
                    m_leafs = new HashSet<>();
                }
                if (!m_leafs.contains(leaf)) {
                    m_leafs.add(leaf);
                }
            }
        }
        return leafCnt();
    }

    public int leafCnt() {
        return (null != m_leafs) ? m_leafs.size() : 0;
    }

    public void addEdge(Vertex<V> src, Vertex<V> dest, E data) {
        Edge e = new Edge(src, dest, data);
        src.addOutGoingEdge(e);
        dest.setIncomingEdge(e);
        if (null == m_leafs) {
            m_leafs = new HashSet();
        }
        m_leafs.remove(src);
        if (1 > dest.getOutDegree()) {
            if (!m_leafs.contains(dest)) {
                m_leafs.add(dest);
            }
        }
    }

    public Collection<Vertex<V>> getLeafs() {
        return (null != m_leafs) ? m_leafs : null;
    }

    private final Vertex<V> m_root;

    private Set<Vertex<V>> m_leafs;

    /**
     * Define interface for getting vertex name during DiGraph.toString().
     *
     * @param <V> vertex type.
     */
    public static interface IPrintVertexName<V> {

        /**
         * Get vertex name to be used for printing graph.
         *
         * @param v vertex instance to print.
         * @return vertex name.
         */
        public String getVertexName(Vertex<V> v);
    }

    /**
     * Define interface for getting edge name during DiGraph.toString().
     *
     * @param <V> vertex type.
     * @param <E> edge type.
     */
    public static interface IPrintEdgeName<V, E> {

        /**
         * Get edge name to be used for printing graph.
         *
         * @param e edge instance to print.
         * @return edge name.
         */
        public String getEdgeName(Edge<V, E> e);
    }

    protected static IPrintVertexName stPrintVertexName = new IPrintVertexName() {

        @Override
        public String getVertexName(Vertex v) {
            return v.toString();
        }

    };
    
    protected static IPrintEdgeName stPrintEdgeName = new IPrintEdgeName() {

        @Override
        public String getEdgeName(Edge e) {
            return e.toString();
        }

    };
    
}
