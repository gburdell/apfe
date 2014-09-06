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

import java.util.LinkedList;
import java.util.List;

/**
 * A vertex with one incoming edge and many outgoing edge.
 *
 * @author gburdell
 * @param <V> vertex data type.
 */
public class Vertex<V> {

    /**
     * Only dup state.
     * @param v vertex state to dup.
     */
    public Vertex(Vertex<V> v) {
        this(v.m_data);
    }

    public Vertex(V dat) {
        m_data = dat;
    }

    public V getData() {
        return m_data;
    }

    public void setIncomingEdge(Edge in) {
        if (null != m_incoming) {
            throw new RuntimeException("Incoming edge already set");
        }
        m_incoming = in;
    }

    public void addOutGoingEdge(Edge out) {
        if (null == m_outgoing) {
            m_outgoing = new LinkedList<>();
        }
        m_outgoing.add(out);
    }
    
    public Edge getIncomingEdge() {
        return m_incoming;
    }

    public int getOutDegree() {
        return (null != m_outgoing) ? m_outgoing.size() : 0;
    }

    public int getInDegree() {
        return (null != m_incoming) ? 1 : 0;
    }

    public boolean isLeaf() {
        return (0 == getOutDegree());
    }

    public List<Edge> getOutGoingEdges() {
        return (0 < getOutDegree()) ? m_outgoing : null;
    }

    /**
     * Remove outgoing edges from this vertex and return them.
     *
     * @return outgoing edges (source vertex is set null).
     */
    public List<Edge> rmOutGoingEdges() {
        if (0 == getOutDegree()) {
            return null;
        }
        List<Edge> outs = new LinkedList<>();
        Vertex<V> v;
        for (Edge e : getOutGoingEdges()) {
            assert (this == e.getSrc());
            e.setSrc(null);
            outs.add(e);
        }
        m_outgoing = null;
        return outs;
    }

    private final V m_data;
    private Edge m_incoming;
    private List<Edge> m_outgoing;

}
