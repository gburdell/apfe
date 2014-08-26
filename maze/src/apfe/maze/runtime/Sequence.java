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
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

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
        m_eles = Util.arrayAsQueue(eles);
    }

    private Sequence(Queue<Acceptor> eles) {
        m_eles = new ArrayDeque<>(eles.size());
        for (Acceptor ele : eles) {
            m_eles.add(ele.create());
        }
    }

    @Override
    public Acceptor create() {
        //deep clone
        return new Sequence(m_eles);
    }

    /**
     * Build sequence entirely in this subgraph. If sequence is successful, then
     * we flatten into parent.
     *
     * @return
     */
    @Override
    protected Graph acceptImpl() {
        boolean ok = true;
        Graph g = getSubgraph(), subg = null;
        Acceptor acc;
        Vertex<State> dest = null;
        Collection<Vertex<State>> srcs = Util.asCollection(getSubgraphRoot());
        List<Vertex<State>> nextSrcs = null;
        while (ok && !m_eles.isEmpty()) {
            acc = m_eles.remove();
            nextSrcs = null;
            for (Vertex src : srcs) {
                subg = acc.accept(src, g);
                if (null != subg) {
                    nextSrcs = Util.addToList(nextSrcs, subg.getLeafs());
                } else {
                    //TODO: sequence failed
                    ok = false;
                }
            }
            srcs = nextSrcs;
        }
        if (ok) {
            //Add leafs from last subgraph to parent.
            //TODO: g.addLeafs(srcs); --or--
            getParentGraph().addLeafs(srcs);
        } else {
            //remove any edges/nodes we added during processing here.
        }
        return ok ? g : null;
    }

    private final Queue<Acceptor> m_eles;
}
