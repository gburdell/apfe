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
import java.util.Collection;
import java.util.Comparator;

/**
 *
 * @author gburdell
 */
public abstract class Acceptor implements Comparator {

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
     * @param start root vertex which is duped as root of subgraph.
     * @return subgraph if accepted else null.
     */
    public Graph accept(Vertex<State, Acceptor> start) {
        //Duplicate data but not edges.
        m_subgraph = new Graph(new Vertex<State, Acceptor>(start.getData()));
        final boolean match = acceptImpl();
        return match ? m_subgraph : null;
    }

    protected abstract boolean acceptImpl();

    protected Token getToken() {
        return getSubgraphRoot().getData().getToken();
    }

    protected Vertex<State, Acceptor> getSubgraphRoot() {
        return getSubgraph().getRoot();
    }

    protected Graph getSubgraph() {
        return m_subgraph;
    }

    @Override
    public String toString() {
        return Class.class.getSimpleName();
    }

    @Override
    public boolean equals(Object obj) {
        boolean eq = (null != obj) && (obj instanceof Acceptor)
                && (getEdgeTypeId() == ((Acceptor)obj).getEdgeTypeId());
        return eq;
    }

    /**
     * Add (accepted) edge to this subgraph.
     *
     * @param src source vertex.
     * @param edge edge to add.
     * @param subg subg vertex (root of subgraph to add).
     */
    protected void addEdge(Vertex<State, Acceptor> src, Acceptor edge, Graph subg) {
        Vertex<State, Acceptor> dest = subg.getRoot(), v;
        Collection<Vertex<State, Acceptor>> leafs;
        if (edge instanceof Terminal) {
            Terminal asTerm = (Terminal) edge;
            if (asTerm.getTokCode() != Token.EOF) {
                //just grab the leaf/dest node so we dont get -term->o-term->
                assert (1 == dest.getOutDegree());
                //leaf node: but with incoming edge, so just grab state.
                v = dest.getOutGoingEdges().get(0).getDest();
                dest = new Vertex<>(v.getData());
            }
            if (getSubgraph().addEdge(src, dest, edge)) {
                leafs = Util.asCollection(dest);
                assert (1 >= leafs.size());
                getSubgraph().addLeafs(leafs);
            }
        } else if (getSubgraph().addEdge(src, dest, edge)) {
            //we only add leafs if we took the subgraph
            leafs = subg.getLeafs();
            getSubgraph().addLeafs(leafs);
        } //else: if we dont add the subgraph then we dont want the leafs either
    }

    protected void setSubGraph(Graph subg) {
        m_subgraph = subg;
    }

    /**
     * A deep clone.
     *
     * @param eles elements to clone.
     * @return deep clone.
     */
    protected static Acceptor[] deepClone(Acceptor eles[]) {
        final int n = eles.length;
        assert 0 < n;
        Acceptor dup[] = new Acceptor[n];
        for (int i = 0; i < n; i++) {
            dup[i] = eles[i].create();
        }
        return dup;
    }

    /**
     * The subgraph we are building with this acceptor.
     */
    private Graph m_subgraph;
    
    /**
     * We'll assign a unique id for each edge type derived from Acceptor.
     */
    private static int stEdgeTypeId = 0;
    
    protected static int getNextEdgeTypeId() {
        return ++stEdgeTypeId;
    }
    
    public abstract int getEdgeTypeId();

    @Override
    public int compare(Object o1, Object o2) {
        assert (null != o1) && (null != o2);
        Acceptor e1 = (Acceptor)o1, e2 = (Acceptor)o2;
        return (e1.getEdgeTypeId() == e2.getEdgeTypeId()) ? 0
                : ((e1.getEdgeTypeId() < e2.getEdgeTypeId()) ? -1 : 1);
    }
}
