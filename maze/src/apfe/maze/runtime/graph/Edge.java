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

import apfe.maze.runtime.Acceptor;
import apfe.maze.runtime.Terminal;
import apfe.maze.runtime.Token;
import static apfe.maze.runtime.Util.isNull;
import static apfe.runtime.Util.downCast;
import java.util.Comparator;

/**
 * A directed edge from source to dest.
 *
 * @author gburdell
 */
public class Edge {

    public Edge(Acceptor edge, Vertex src, Vertex dest) {
        m_data = edge;
        m_src = src;
        m_dest = dest;
    }

    /**
     * Add vertices to edge, assuming they are currently null.
     *
     * @param src source vertex.
     * @param dest destination vertex.
     * @return this edge.
     */
    public Edge addVertices(Vertex src, Vertex dest) {
        assert ((null == getSrc()) && (null == getDest()));
        setSrc(src);
        setDest(dest);
        return this;
    }

    public void clear() {
        setDest(null);
        setSrc(null);
    }

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

    public Edge(Acceptor edge) {
        this(edge, null, null);
    }

    public Acceptor getData() {
        return m_data;
    }

    @Override
    public boolean equals(Object to) {
        assert (to instanceof Edge);
        Edge asE = downCast(to);
        Acceptor edges[] = {getData(), asE.getData()};
        if (isNull(edges)) {
            return true;
        }
        final boolean eq = (edges[0].getEdgeTypeId() == edges[1].getEdgeTypeId());
        return eq;
    }

        public String getEdgeName() {
        String s;
        if (getData() instanceof Terminal) {
            Terminal asTerm = downCast(getData());
            if (asTerm.getTokCode() != Token.EOF) {
                s = "'" + asTerm.getMatched().getText() + "'";
            } else {
                s = "'<EOF>'";
            }
        } else {
            s = getData().getClass().getSimpleName();
        }
        return s;
    }

        public Comparator<Edge> getComparator() {
        return stCompare;
    }

    private static final Comparator<Edge> stCompare = new Comparator<Edge>() {
        @Override
        public int compare(Edge o1, Edge o2) {
            Edge asE[] = new Edge[]{o1, o2};
            Acceptor edges[] = {asE[0].getData(), asE[1].getData()};
            if (isNull(edges)) {
                return 0;
            }
            return (edges[0].getEdgeTypeId() == edges[1].getEdgeTypeId()) ? 0
                    : ((edges[0].getEdgeTypeId() < edges[1].getEdgeTypeId()) ? -1 : 1);
        }
    };
    private Vertex m_src;
    private Vertex m_dest;
    private final Acceptor m_data;
}
