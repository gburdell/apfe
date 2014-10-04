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

import static apfe.maze.runtime.Util.isNull;

/**
 * Base class of all edges.
 *
 * @author gburdell
 */
public abstract class Edge {

    protected Edge() {
    }

    protected Edge(Vertex from, Vertex to) {
        addVertices(from, to);
    }

    final public void addVertices(Vertex src, Vertex dest) {
        assert isNull(m_from);
        assert isNull(m_to);
        m_from = src;
        m_to = dest;
        addEdge();
    }

    private void addEdge() {
        m_from.addOutgoing(this);
        m_to.addIncoming(this);
    }

    public Vertex getSrc() {
        return m_from;
    }

    public Vertex getDest() {
        return m_to;
    }

    /**
     * Check if the little rodent can pass through this edge.
     * If so, the rat's state is possibly updated (i.e., consume token).
     * @param visitor out little rodent wandering the maze.
     * @return true if rat can pass this edge (and update its state, if applicable).
     */
    public abstract boolean canPassThrough(Rat visitor);

    private Vertex m_from, m_to;
}
