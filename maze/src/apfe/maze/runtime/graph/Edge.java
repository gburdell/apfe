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
package apfe.maze.runtime.graph;

import java.util.Comparator;

/**
 * A directed edge from source to dest.
 *
 * @author gburdell
 */
public abstract class Edge {

    protected Edge(Vertex src, Vertex dest) {
        m_src = src;
        m_dest = dest;
    }

    protected Edge() {
        this(null, null);
    }

    public abstract String getEdgeName();

    public abstract Comparator<Edge> getComparator();

    /**
     * Add vertices to edge, assuming they are currently null.
     *
     * @param src source vertex.
     * @param dest destination vertex.
     * @return this edge.
     */
    public Edge addVertices(Vertex src, Vertex dest) {
        assert ((null == getSrc()) && (null == getDest()));
        m_src = src;
        m_dest = dest;
        return this;
    }

    @Override
    public abstract boolean equals(Object to);

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Vertex getSrc() {
        return m_src;
    }

    public Vertex getDest() {
        return m_dest;
    }

    public void setSrc(Vertex src) {
        if (null != src) {
            assert (null == m_src);
        }
        m_src = src;
    }

    public void setDest(Vertex dest) {
        if (null != dest) {
            assert (null == m_dest);
        }
        m_dest = dest;
    }

    private Vertex m_src;
    private Vertex m_dest;
}
