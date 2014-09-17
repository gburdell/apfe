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

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * A vertex with one incoming edge and many outgoing edge.
 *
 * @author gburdell
 */
public abstract class VertexBase implements Comparator<VertexBase> {

    protected VertexBase() {
    }

    public abstract String getVertexName();

    @Override
    public abstract boolean equals(Object to);
    
    public void setIncomingEdge(EdgeBase in) {
        if (null != m_incoming) {
            throw new RuntimeException("Incoming edge already set");
        }
        m_incoming = in;
    }

    public void addOutGoingEdge(EdgeBase out) {
        if (null == m_outgoing) {
            m_outgoing = new LinkedList<>();
        }
        m_outgoing.add(out);
    }

    public EdgeBase getIncomingEdge() {
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

    public List<EdgeBase> getOutGoingEdges() {
        return (0 < getOutDegree()) ? m_outgoing : null;
    }

    private EdgeBase m_incoming;
    private List<EdgeBase> m_outgoing;
}
