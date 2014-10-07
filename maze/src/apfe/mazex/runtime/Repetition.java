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
package apfe.mazex.runtime;

import apfe.mazex.runtime.graph.Graph;
import apfe.mazex.runtime.graph.Vertex;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

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
            extends Util.Triplet<Vertex, Acceptor, Graph> {

        public Candidate(Vertex src, Acceptor edge, Graph subg) {
            super(src, edge, subg);
        }

        public Vertex getSrc() {
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
        Collection<Vertex> leafs;
        for (Candidate cand : cands) {
            leafs = cand.getSubGraph().getMarkedVertices();
            for (Vertex vx : leafs) {
                if ((null != vx) && (vx.getData().getPos() > lastPos)) {
                    lastPos = vx.getData().getPos();
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

    private void addEpsilonEdge(Vertex src) {
        getSubgraph().addEpsilon(src);
    }

    @Override
    protected boolean acceptImpl() {
        int acceptedCnt = 0;
        boolean matched = true;
        Graph subg;
        Collection<? extends Vertex> srcs;
        //keep track of vertex where we accepted.
        Stack<Vertex> acceptedSrcs = new Stack<>();
        m_lastPos = getSubgraphRoot().getData().getPos();
        List<Candidate> cands;
        Acceptor edge;
        Vertex accSrc;
        while (matched) {
            edge = m_rep.create();
            cands = new LinkedList<>();
            srcs = Util.toList(getSubgraph().getMarkedVertices());
            for (Vertex src : srcs) {
                subg = edge.accept(src);
                //build up candidates
                if (null != subg) {
                    cands.add(new Candidate(src, edge, subg));
                }
            }
            matched = hasChangedState(cands);
            if (matched) {
                for (Candidate cand : cands) {
                    subg = cand.getSubGraph();
                    assert (null != subg);
                    accSrc = cand.getSrc();
                    addEdge(accSrc, cand.getEdge(), subg);
                    acceptedSrcs.push(accSrc);
                }
                acceptedCnt++;
            }
        }
        //Add epsilon edges from accepted.
        //If zeroOrMore and accepted one, we hav epsilon on root
        if (!m_oneOrMore && (0 < acceptedSrcs.size())) {
            //no epsilon on the first one if oneOrMore.
            acceptedSrcs.push(getSubgraphRoot());
        }
        //process all but the last one
        while (1 < acceptedSrcs.size()) {
            accSrc = acceptedSrcs.pop();
            addEpsilonEdge(accSrc);
        }
        return (!m_oneOrMore || (0 < acceptedCnt));
    }

    private final Acceptor m_rep;
    private final boolean m_oneOrMore;

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    /*package*/ static final int stEdgeTypeId = getNextEdgeTypeId();

}