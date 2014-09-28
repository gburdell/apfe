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

import apfe.maze.runtime.graph.DiGraph;
import apfe.maze.runtime.graph.Edge;
import apfe.maze.runtime.graph.Vertex;
import static apfe.runtime.Util.downCast;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author gburdell
 */
public class Graph extends DiGraph {

    /**
     * Create graph rooted at the lexer start position.
     *
     * @param lex root lexer.
     */
    public Graph(Scanner lex) {
        this(new State(lex, 0));
    }

    /**
     * Create graph rooted at specified state.
     *
     * @param st root state.
     */
    public Graph(State st) {
        this(new V(st));
    }

    /**
     * Create graph which is rooted at specified vertex.
     *
     * @param vtx root vertex.
     */
    public Graph(V vtx) {
        super(vtx);
    }

    @Override
    public V getRoot() {
        return downCast(super.getRoot());
    }

    public int addLeafsAndEpsilons(Iterable<? extends Vertex> leafs) {
        if (null != leafs) {
            for (Vertex leaf : leafs) {
                if (leaf.isLeaf()) {
                    addLeaf(leaf);
                } else {
                    addEpsilon(leaf);
                }
            }
        }
        return leafCnt();
    }

    public Collection<? extends Vertex> getLeafsAndEpsilons() {
        if ((null == m_epsilons) || m_epsilons.isEmpty()) {
            return super.getLeafs();
        }
        List<V> all = new LinkedList(hasLeafs() ? super.getLeafs() : stEmpty);
        all.addAll(m_epsilons);
        return all;
    }

    private static final Collection<? extends Vertex> stEmpty = new LinkedList();

    public void addEpsilon(V vx) {
        if (null == m_epsilons) {
            m_epsilons = new HashSet<>();
        }
        if (!hasEpsilon(vx)) {
            m_epsilons.add(vx);
        }
    }

    public boolean hasEpsilon(V vx) {
        return (null != m_epsilons) && m_epsilons.contains(vx);
    }

    private static <T> boolean isNull(T ele[]) {
        return (null == ele[0]) && (null == ele[1]);
    }

    public boolean addEdge(V src, V dest, Acceptor edge) {
        E realEdge = new E(edge);
        return addEdge(src, realEdge, dest);
    }

    /**
     * Add edge to this graph iff. it does not already exist.
     *
     * @param src source vertex.
     * @param edge edge from src to dest.
     * @param dest destination vertex.
     * @return true if this edge is added; else false since edge already exists.
     */
    @Override
    public boolean addEdge(Vertex src, Edge edge, Vertex dest) {
        return super.addEdge(src, edge, dest);
    }

    private Set<V> m_epsilons;

    /**
     * The Vertex (aka node).
     */
    public static class V extends Vertex {

        public V(State data) {
            m_data = data;
        }

        public V(V root) {
            this(root.getData());
        }

        public V getDestVertex(int ix) {
            return downCast(getOutGoingEdges().get(ix).getDest());
        }

        public V getFirstDest() {
            return getDestVertex(0);
        }

        public State getData() {
            return m_data;
        }

        @Override
        public String getVertexName() {
            return Integer.toString(getData().getPos());
        }

        @Override
        public boolean equals(Object to) {
            assert (to instanceof V);
            V asV = downCast(to);
            State states[] = {getData(), asV.getData()};
            if (isNull(states)) {
                return true;
            }
            return (states[0].getPos() == states[1].getPos());
        }

        /**
         * Since the Vertex hashcode is used as key for leafs: we want to make
         * sure these are unique by actual instance (default hashcode). That is:
         * Vertices should not have same hashcode even if they refer to same
         * lexer state.
         *
         * @return instance-specific hashcode.
         */
        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public void clear() {
            m_data = null;
            super.clear();
        }

        private State m_data;

        @Override
        public Comparator<Vertex> getComparator() {
            return stCompare;
        }

        private static final Comparator<Vertex> stCompare = new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                V asV[] = {downCast(o1), downCast(o2)};
                State states[] = {asV[0].getData(), asV[1].getData()};
                if (isNull(states)) {
                    return 0;
                }
                return (states[0].getPos() == states[1].getPos()) ? 0
                        : ((states[0].getPos() < states[1].getPos()) ? -1 : 1);
            }
        };
    }

    public static class E extends Edge {

        public E(Acceptor edge) {
            m_data = edge;
        }

        public Acceptor getData() {
            return m_data;
        }

        @Override
        public boolean equals(Object to) {
            assert (to instanceof E);
            E asE = downCast(to);
            Acceptor edges[] = {getData(), asE.getData()};
            if (isNull(edges)) {
                return true;
            }
            final boolean eq = (edges[0].getEdgeTypeId() == edges[1].getEdgeTypeId());
            return eq;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public String getEdgeName() {
            String s;
            if (getData() instanceof Terminal) {
                Terminal asTerm = downCast(getData());
                if (asTerm.getTokCode() != Token.EOF) {
                    s = "'" + asTerm.getMatched().getText() + "'";
                } else {
                    s = "'<EOF>'";
                }
            } else {
                s = getData().getClass().getSimpleName();
            }
            return s;
        }

        private final Acceptor m_data;

        @Override
        public Comparator<Edge> getComparator() {
            return stCompare;
        }

        private static final Comparator<Edge> stCompare = new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                E asE[] = {downCast(o1), downCast(o2)};
                Acceptor edges[] = {asE[0].getData(), asE[1].getData()};
                if (isNull(edges)) {
                    return 0;
                }
                return (edges[0].getEdgeTypeId() == edges[1].getEdgeTypeId()) ? 0
                        : ((edges[0].getEdgeTypeId() < edges[1].getEdgeTypeId()) ? -1 : 1);
            }
        };
    }

}
