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
import static apfe.runtime.Util.downCast;
import java.util.Collection;

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
        Collection<? extends Vertex> srcs;
        boolean anyAccepted;
        for (Acceptor edge : m_eles) {
            anyAccepted = false;
            srcs = Util.toList(getSubgraph().getMarkedVertices());
            for (Vertex src : srcs) {
                subg = edge.accept(src);
                if (null != subg) {
                    addEdge(src, edge, subg);
                    anyAccepted = true;
                }
            }
            if (!anyAccepted) {
                return false;
            }
        }
        return true;
    }

    private final Acceptor m_eles[];

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    public static final int stEdgeTypeId = getNextEdgeTypeId();

}
