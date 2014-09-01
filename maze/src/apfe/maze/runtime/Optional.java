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

/**
 * An Acceptor with optional semantics.
 *
 * @author gburdell
 */
public class Optional extends Acceptor {

    /**
     * Optionally accept.
     *
     * @param opt element to optionally accept.
     */
    public Optional(Acceptor opt) {
        m_opt = opt;
    }

    @Override
    public Acceptor create() {
        return new Optional(m_opt.create());
    }

    @Override
    protected boolean acceptImpl() {
        Vertex<State> dest = new Vertex<>(getSubgraphRoot());
        Vertex<State> src = getSubgraphRoot();
        //always accept nothing.
        getSubgraph().addEdge(src, dest, new Epsilon());
        Graph subg = m_opt.accept(getSubgraphRoot());
        if (null != subg) {
            addEdge(src, m_opt, subg);
        }
        return true;
    }

    private final Acceptor m_opt;

    /**
     * Edge which consumes nothing.
     */
    public static class Epsilon extends Acceptor {

        @Override
        public Acceptor create() {
            return new Epsilon();
        }

        @Override
        protected boolean acceptImpl() {
            return true;
        }
    }

}