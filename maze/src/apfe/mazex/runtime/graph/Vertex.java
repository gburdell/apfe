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
package apfe.mazex.runtime.graph;

import apfe.mazex.runtime.State;
import static apfe.mazex.runtime.Util.isNull;
import static apfe.mazex.runtime.graph.Graph.depthFirst;
import static apfe.runtime.Util.downCast;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * A vertex with one incoming edge and many outgoing edge.
 *
 * @author gburdell
 */
public class Vertex {

    public Vertex(State data) {
        m_data = data;
    }

    public Vertex(Vertex root) {
        this(root.getData());
    }

    public String getVertexName() {
        return Integer.toString(getData().getPos());
    }

    public Vertex getDestVertex(int ix) {
        return getOutGoingEdges().get(ix).getDest();
    }

    public Vertex getFirstDest() {
        return getDestVertex(0);
    }

    public State getData() {
        return m_data;
    }

    @Override
    public boolean equals(Object to) {
        assert (to instanceof Vertex);
        Vertex asVx = downCast(to);
        State states[] = {getData(), asVx.getData()};
        if (isNull(states)) {
            return true;
        }
        return (states[0].getPos() == states[1].getPos());
    }

    /**
     * Since the Vertex hashcode is used as key for leafs: we want to make sure
     * these are unique by actual instance (default hashcode). That is: Vertices
     * should not have same hashcode even if they refer to same lexer state.
     *
     * @return instance-specific hashcode.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void setIncomingEdge(Edge in) {
        if (null != m_incoming) {
            throw new RuntimeException("Incoming edge already set");
        }
        m_incoming = in;
    }

    public void clearIncomingEdge() {
        m_incoming = null;
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

    @Override
    public String toString() {
        StringBuilder lstr = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        depthFirst(lstr, sb, this);
        return lstr.toString();
    }

    public void clear() {
        m_incoming = null;
        m_outgoing = null;
        m_data = null;
    }

    /**
     * Add leafs found from this vertex.
     *
     * @param leafs existing leaf set.
     * @return existing leafs and any new ones added.
     */
    public Collection<Vertex> addLeafs(Collection<Vertex> leafs) {
        if (isLeaf()) {
            if (leafs.contains(this)) {
                leafs.add(this);
            }
        } else {
            for (Edge edge : getOutGoingEdges()) {
                Vertex vx = edge.getDest();
                if (null != vx) {
                    leafs = vx.addLeafs(leafs);
                }
            }
        }
        return leafs;
    }

    public Collection<Vertex> findLeafs() {
        return addLeafs(new HashSet<Vertex>());
    }

    public Comparator<Vertex> getComparator() {
        return stCompare;
    }

    private static final Comparator<Vertex> stCompare = new Comparator<Vertex>() {
        @Override
        public int compare(Vertex o1, Vertex o2) {
            Vertex asV[] = {downCast(o1), downCast(o2)};
            State states[] = {asV[0].getData(), asV[1].getData()};
            if (isNull(states)) {
                return 0;
            }
            return (states[0].getPos() == states[1].getPos()) ? 0
                    : ((states[0].getPos() < states[1].getPos()) ? -1 : 1);
        }
    };

    private State m_data;
    private Edge m_incoming;
    private List<Edge> m_outgoing;
}
