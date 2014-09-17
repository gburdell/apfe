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
import apfe.maze.runtime.graph.EdgeBase;
import apfe.maze.runtime.graph.VertexBase;
import static apfe.runtime.Util.downCast;

/**
 *
 * @author gburdell
 */
public class Graph extends DiGraph {
    private static <T> boolean isNull(T ele[]) {
        return (null == ele[0]) && (null == ele[1]);
    }
    /**
     * The Vertex (aka node).
     */
    public static class V extends VertexBase {

        public V(State data) {
            m_data = data;
        }

        public State getData() {
            return m_data;
        }

        @Override
        public String getVertexName() {
            return Integer.toString(getData().getPos());
        }

        @Override
        public int compare(VertexBase o1, VertexBase o2) {
            V asV[] = {downCast(o1), downCast(o2)};
            State states[] = {asV[0].getData(), asV[1].getData()};
            if (isNull(states)) {
                return 0;
            }
            return (states[0].getPos() == states[1].getPos()) ? 0
                    : ((states[0].getPos() < states[1].getPos()) ? -1 : 1);
        }

        @Override
        public boolean equals(Object to) {
            V asV = downCast(to);
            State states[] = {getData(), asV.getData()};
            if (isNull(states)) {
                return true;
            }
            return (states[0].getPos() == states[1].getPos());
        }

        private final State m_data;
    }

    private static class E extends EdgeBase {

        public E(V src, Acceptor edge, V dest) {
            super(src, dest);
            m_data = edge;
        }

        public Acceptor getData() {
            return m_data;
        }

        @Override
        public boolean equals(Object to) {
            E asE = downCast(to);
            Acceptor edges[] = {getData(), asE.getData()};
            if (isNull(edges)) {
                return true;
            }
            return (edges[0].getEdgeTypeId() == edges[1].getEdgeTypeId());
        }

        @Override
        public String getEdgeName() {
            return getData().toString();
        }

        @Override
        public int compare(EdgeBase o1, EdgeBase o2) {
            E asE[] = {downCast(o1), downCast(o2)};
            Acceptor edges[] = {asE[0].getData(), asE[1].getData()};
            if (isNull(edges)) {
                return 0;
            }
            return (edges[0].getEdgeTypeId()== edges[1].getEdgeTypeId()) ? 0
                    : ((edges[0].getEdgeTypeId() < edges[1].getEdgeTypeId()) ? -1 : 1);
        }

        private final Acceptor m_data;
    }

    public Graph(Scanner lex) {
        this(new State(lex, 0));
    }

    public Graph(State st) {
        super(st);
    }

    public Graph(Vertex<State, Acceptor> st) {
        super(st);
    }

    /**
     * Create edge in existing graph. Add dest as leaf vertex, if it is a leaf
     * node.
     * <B>NOTE:</B> This implementation only checks if -edge>(dest) exists. It
     * does not check if subtree rooted by edge exists. It just checks one-level
     * deep.
     *
     * @param src source vertex.
     * @param dest dest vertex
     * @param edge edge connecting src to dest.
     * @return true if edge was added.
     */
    @Override
    public boolean addEdge(Vertex<State, Acceptor> src, Vertex<State, Acceptor> dest, Acceptor edge) {
        boolean add = (1 > src.getOutDegree());
        if (!add) {
            Acceptor ea;
            for (Edge<State, Acceptor> exists : src.getOutGoingEdges()) {
                //dont add if we already have edge+dest
                add = !(exists.getData().equals(edge)
                        && exists.getDest().getData().equals(dest.getData()));
                if (!add) {
                    //curious: if this is the case
                    assert (exists.getDest().getOutDegree() == dest.getOutDegree());
                    //assert dest.isLeaf(); //curious??
                    return false;
                }
            }
        }
        return super.addEdge(src, dest, edge);
    }

    static {
        stPrintEdgeName = new IPrintEdgeName<State, Acceptor>() {
            @Override
            public String getEdgeName(Edge<State, Acceptor> e) {
                String s;
                if (e.getData() instanceof Terminal) {
                    Terminal asTerm = (Terminal) e.getData();
                    if (asTerm.getTokCode() != Token.EOF) {
                        s = "'" + ((Terminal) e.getData()).getMatched().getText() + "'";
                    } else {
                        s = "'<EOF>'";
                    }
                } else {
                    s = e.getData().getClass().getSimpleName();
                }
                return s;
            }
        };
        stPrintVertexName = new IPrintVertexName<State, Acceptor>() {
            @Override
            public String getVertexName(Vertex<State, Acceptor> v) {
                return Integer.toString(v.getData().getPos());
            }
        };
    }
}
