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

    @Override
    protected boolean acceptImpl() {
        int acceptedCnt = 0;
        boolean matched = true;
        Graph subg;
        Vertex<State> dest;
        Collection<Vertex<State>> srcs = Util.asCollection(getSubgraphRoot());
        List<Vertex<State>> nextSrcs;
        while (matched) {
            nextSrcs = null;
            matched = false;
            for (Vertex<State> src : srcs) {
                dest = new Vertex<>(src);
                //always accept nothing
                getSubgraph().addEdge(src, dest, new Optional.Epsilon());
                subg = m_rep.accept(src);
                if (null != subg) {
                    //String dbg = subg.toString();
                    addEdge(src, m_rep, subg);
                    //dbg = getSubgraph().toString();
                    nextSrcs = Util.addToList(nextSrcs, getSubgraph().getLeafs());
                    matched = true;
                }
            }
            if (matched) {
                acceptedCnt++;
            }
            srcs = nextSrcs;
        }
        return (!m_oneOrMore || (0 < acceptedCnt));
    }

    private final Acceptor m_rep;
    private final boolean m_oneOrMore;
}
