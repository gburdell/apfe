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


/**
 * A sequence of one or more AcceptorBase.
 *
 * @author gburdell
 */
public class Alternates extends Acceptor {

    /**
     * Acceptor with alternative semantics.
     *
     * @param alts series of alternatives.
     */
    public Alternates(Acceptor... alts) {
        m_alts = alts;
    }

    @Override
    public Acceptor create() {
        return new Alternates(deepClone(m_alts));
    }

    /**
     * Attempt to match alternatives and build into this subgraph.
     *
     * @return true if any accepted, else false.
     */
    @Override
    protected boolean acceptImpl() {
        Graph subg;
        Vertex src = getSubgraphRoot();
        int cnt = 0;
        for (Acceptor acc : m_alts) {
            subg = acc.accept(src);
            if (null != subg) {
                addEdge(src, acc, subg);
                cnt++;
            }
        }
        return (0 < cnt);
    }

    private final Acceptor m_alts[];

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();
}