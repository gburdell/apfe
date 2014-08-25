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

import apfe.maze.runtime.Util;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A directed graph with time-efficient leaf detection.
 *
 * @author gburdell
 * @param <V> vertex data type.
 * @param <E> edge data type.
 */
public class DiGraph<V, E> {

    public DiGraph(Vertex root) {
        m_root = root;
    }

    public DiGraph(V root) {
        this(new Vertex(root));
    }

    public Vertex<V> getRoot() {
        return m_root;
    }

    public int addLeafs(Iterable<Vertex> leafs) {
        if (null != leafs) {
            for (Vertex leaf  : leafs) {
                if (null == m_leafs) {
                    m_leafs = new HashSet<>();
                }
                if (! m_leafs.contains(leaf)) {
                    m_leafs.add(leaf);
                }
            }
        }
        return leafCnt();
    }

    public int leafCnt() {
        return (null != m_leafs) ? m_leafs.size() : 0;
    }
    
    public void addEdge(Vertex src, Vertex dest, E data) {
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

    public Iterator<Vertex> getLeafs() {
        return (null != m_leafs) ? m_leafs.iterator() : stEmpty.iterator();
    }

    private final Vertex m_root;

    private Set<Vertex> m_leafs;

    private static final Iterable<Vertex> stEmpty = new Util.EmptyCollection<>();
}
