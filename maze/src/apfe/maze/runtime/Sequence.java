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
import apfe.maze.sv2009.ITokenCodes;
import java.util.Collection;
import java.util.List;

/**
 * A sequence of one or more AcceptorBase.
 *
 * @author gburdell
 */
public class Sequence extends Acceptor {

    /**
     * Acceptor with sequence semantics.
     *
     * @param eles sequence of Acceptor prescribed.
     */
    public Sequence(Acceptor... eles) {
        m_eles = eles;
    }

    @Override
    public Acceptor create() {
        return new Sequence(deepClone(m_eles));
    }

    /**
     * Attempt to match sequence and build into this subgraph.
     *
     * @return true if accepted, else false.
     */
    @Override
    protected boolean acceptImpl() {
        Graph subg;
        Vertex<State,Acceptor> dest = null;
        Collection<Vertex<State,Acceptor>> srcs = Util.asCollection(getSubgraphRoot());
        List<Vertex<State,Acceptor>> nextSrcs;
        boolean anyAccepted;
        for (Acceptor edge : m_eles) {
            nextSrcs = null;
            anyAccepted = false;
            for (Vertex<State,Acceptor> src : srcs) {
                subg = edge.accept(src);
                if (null != subg) {
                    //String dbg = subg.toString();
                    dest = subg.getRoot();
                    //dbg = getSubgraph().toString();
                    addEdge(src, edge, subg);
                    //dbg = getSubgraph().toString();
                    nextSrcs = Util.addToList(nextSrcs, getSubgraph().getLeafs());
                    anyAccepted = true;
                }
            }
            if (!anyAccepted) {
                return false;
            }
            srcs = nextSrcs;
        }
        return true;
    }

    private final Acceptor m_eles[];
}
