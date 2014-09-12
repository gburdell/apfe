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
import java.util.LinkedList;
import java.util.List;

/**
 * An Acceptor with repetition semantics.
 *
 * @author gburdell
 */
public class Repetition extends Acceptor {

    /**
     * Accept 0 or more.
     *
     * @param rep element to repetitively accept.
     */
    public Repetition(Acceptor rep) {
        this(rep, false);
    }

    /**
     * Accept 0/1 or more.
     *
     * @param rep element to repetitively accept.
     * @param oneOrMore specify (true) for rep+ semantics, else rep*.
     */
    public Repetition(Acceptor rep, boolean oneOrMore) {
        m_rep = rep;
        m_oneOrMore = oneOrMore;
    }

    @Override
    public Acceptor create() {
        return new Repetition(m_rep.create(), m_oneOrMore);
    }

    private static class Candidate
            extends Util.Triplet<Vertex<State, Acceptor>, Acceptor, Graph> {

        public Candidate(Vertex<State, Acceptor> src, Acceptor edge, Graph subg) {
            super(src, edge, subg);
        }

        public Vertex<State, Acceptor> getSrc() {
            return e1;
        }

        public Acceptor getEdge() {
            return e2;
        }

        public Graph getSubGraph() {
            return e3;
        }
    }

    /**
     * Go through leafs of subgs to check if leaf State has advanced past the
     * last iteration of hasChangedState.
     *
     * @param cands collection of candidate subgraphs.
     * @return true if at least one of these candidates has accepted a new
     * token.
     */
    private boolean hasChangedState(Collection<Candidate> cands) {
        if ((null == cands) || cands.isEmpty()) {
            return false;
        }
        int lastPos = m_lastPos;
        Collection<Vertex<State, Acceptor>> leafs;
        for (Candidate cand : cands) {
            leafs = cand.getSubGraph().getLeafs();
            for (Vertex<State, Acceptor> v : leafs) {
                if ((null != v) && (v.getData().getPos() > lastPos)) {
                    lastPos = v.getData().getPos();
                }
            }
        }
        assert (lastPos >= m_lastPos);  //we dont go backwards
        final boolean hasChanged = (lastPos > m_lastPos);
        m_lastPos = lastPos;
        return hasChanged;
    }

    /**
     * Track last (farthest) State from invocation of acceptImpl to last
     * iteration of hasAdvancedState.
     */
    private int m_lastPos;

    private void addEpsilonEdge(Vertex<State, Acceptor> src) {
        //add empty edge
        Vertex<State, Acceptor> dest = new Vertex<>(src);
        Acceptor nullEdge = new Optional.Epsilon();
        getSubgraph().addEdge(src, dest, nullEdge);
    }

    @Override
    protected boolean acceptImpl() {
        int acceptedCnt = 0;
        boolean matched = true;
        Graph subg;
        Collection<Vertex<State, Acceptor>> srcs = Util.asCollection(getSubgraphRoot());
        m_lastPos = getSubgraphRoot().getData().getPos();
        List<Candidate> cands;
        Acceptor edge;
        if (!m_oneOrMore) {
            //if 0(or more), we always add an epsilon edge
            addEpsilonEdge(getSubgraphRoot());
        }
        while (matched) {
            edge = m_rep.create();
            cands = new LinkedList<>();
            for (Vertex<State, Acceptor> src : srcs) {
                subg = edge.accept(src);
                //build up candidates
                if (null != subg) {
                    cands.add(new Candidate(src, edge, subg));
                }
            }
            matched = hasChangedState(cands);
            if (matched) {
                for (Candidate cand : cands) {
                    if (0 < acceptedCnt) { // for 1(or more), wait until after 1st
                        //add empty edge
                        addEpsilonEdge(cand.getSrc());
                    }
                    subg = cand.getSubGraph();
                    assert (null != subg);
                    addEdge(cand.getSrc(), cand.getEdge(), subg);
                }
                srcs = getSubgraph().getLeafs();
                acceptedCnt++;
            }
        }
        return (!m_oneOrMore || (0 < acceptedCnt));
    }

    private static class LeafFilter implements Graph.IVertexFilter<State, Acceptor> {

        @Override
        public boolean pass(Vertex<State, Acceptor> v) {
            return true;//!(Optional.incomingEdgeIsEpsilon(v));
        }

    }

    private static final LeafFilter stFilter = new LeafFilter();

    private final Acceptor m_rep;
    private final boolean m_oneOrMore;
}
