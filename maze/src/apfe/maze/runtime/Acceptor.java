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

import java.util.Collection;
import apfe.maze.runtime.graph.Vertex;
import apfe.maze.runtime.Graph.V;
import apfe.maze.runtime.graph.Edge;
import static apfe.runtime.Util.downCast;

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
     * @param start root vertex which is duped as root of subgraph.
     * @return subgraph if accepted else null.
     */
    public Graph accept(V start) {
        //Duplicate data but not edges.
        m_subgraph = new Graph(new V(start.getData()));
        final boolean match = acceptImpl();
        return match ? m_subgraph : null;
    }

    protected abstract boolean acceptImpl();

    protected Token getToken() {
        return getSubgraphRoot().getData().getToken();
    }

    protected V getSubgraphRoot() {
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
     * Add (accepted) edge to this subgraph.
     *
     * @param src source vertex.
     * @param edge edge to add.
     * @param subg subg vertex (root of subgraph to add).
     */
    protected void addEdge(V src, Acceptor edge, Graph subg) {
        V dest = subg.getRoot(), v;
        Collection<? extends Vertex> leafs;
        final int edgeTypeId = edge.getEdgeTypeId();
        if (edgeTypeId == Terminal.stEdgeTypeId) {
            Terminal asTerm = (Terminal) edge;
            if (asTerm.getTokCode() != Token.EOF) {
                //just grab the leaf/dest node so we dont get -term->o-term->
                assert (1 == dest.getOutDegree());
                //leaf node: but with incoming edge, so just grab state.
                v = dest.getFirstDest();
                dest = new V(v.getData());
            }
            if (getSubgraph().addEdge(src, dest, edge)) {
                //TODO: this assert/check that adding Terminal does not
                //need explicit add of leafs
                leafs = Util.<Vertex>asCollection(dest);
                assert (1 >= leafs.size());
                int before = getSubgraph().getLeafs().size();
                getSubgraph().addLeafs(leafs);
                int after = getSubgraph().getLeafs().size();
                assert before == after;
            }
        } else if (edgeTypeId == Optional.stEdgeTypeId
                || edgeTypeId == Repetition.stEdgeTypeId) {
            /*
             * Handle case: (src)->Optional->(dest)...
             */
            collapse(src, dest);
        } else {
            if (getSubgraph().addEdge(src, dest, edge)) {
                //we only add leafs if we took the subgraph
                leafs = subg.getLeafs();
                getSubgraph().addLeafs(leafs);
            }
        } //else: if we dont add the subgraph then we dont want the leafs either
    }

    /**
     * Move the dest outgoing edges to src.
     * dest becomes an orphan afterwards.
     * @param src source vertex.
     * @param dest dest vertex.
     */
    private void collapse(V src, V dest) {
        assert (null != src && null != dest);
        assert src.getData().equals(dest.getData());
        assert (1 > dest.getInDegree());
        V nextDest;
        for (Edge edge : dest.getOutGoingEdges()) {
            nextDest = downCast(edge.getDest());
            nextDest.clearIncomingEdge();
            edge.clear();
            if (getSubgraph().addEdge(src, edge, nextDest)) {
                /*
                 * NOTE:
                 * Since we might not add this edge (isomorphism),
                 * then we cannot blindly just add all the leafs of 'subg'.
                 * This is a very expensive operation here.
                 */
                getSubgraph().addLeafs(nextDest.findLeafs());
            }
        }
        dest.clear();
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
}
