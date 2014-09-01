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
    public Graph accept(Vertex<State> start) {
        //Duplicate data but not edges.
        m_subgraph = new Graph(new Vertex<>(start.getData()));
        final boolean match = acceptImpl();
        return match ? m_subgraph : null;
    }

    protected abstract boolean acceptImpl();

    protected Token getToken() {
        return getSubgraphRoot().getData().getToken();
    }

    protected Vertex<State> getSubgraphRoot() {
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
     * @param src   source vertex.
     * @param edge  edge to add.
     * @param subg  subg vertex (root of subgraph to add).
     */
    protected void addEdge(Vertex<State> src, Acceptor edge, Graph subg) {
        Vertex<State> dest = subg.getRoot(), v;
        Collection<Vertex<State>> leafs;
        if (edge instanceof Terminal) {
            //just grab the leaf/dest node so we dont get -term->o-term->
            assert (1 == dest.getOutDegree());
            //leaf node: but with incoming edge, so just grab state.
            v = dest.getOutGoingEdges().get(0).getDest();
            dest = new Vertex(v.getData());
            leafs = Util.asCollection(dest);
        } else if (edge instanceof NonTerminal) {
            leafs = subg.getLeafs();
            /*TODO: let the nonterminal deal with whether to add or not.
            if (edge.getSubgraph() == subg) {
                //Dont create another edge to same nonterminal
                assert (0 == dest.getInDegree());
                setSubGraph(subg);
            }
            */
        } else {
            leafs = subg.getLeafs();
        }
        getSubgraph().addEdge(src, dest, edge);
        getSubgraph().addLeafs(leafs);
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
}