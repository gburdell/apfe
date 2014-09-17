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

import apfe.maze.runtime.Util;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * A directed graph.
 *
 * @author gburdell
 */
public abstract class DiGraph {

    public DiGraph(VertexBase root) {
        m_root = root;
    }

    public VertexBase getRoot() {
        return m_root;
    }

    public int addLeafs(Iterable<VertexBase> leafs) {
        if (null != leafs) {
            for (VertexBase leaf : leafs) {
                addLeaf(leaf);
            }
        }
        return leafCnt();
    }

    private void addLeaf(VertexBase leaf) {
        if (null == m_leafs) {
            m_leafs = new HashSet<>();
        }
        assert leaf.isLeaf();
        if (!hasLeaf(leaf)) {
            m_leafs.add(leaf);
        }
    }

    public int leafCnt() {
        return (null != m_leafs) ? m_leafs.size() : 0;
    }

    /**
     * Create edge in existing graph. Add dest as leaf vertex, if it is a leaf
     * node.
     * <B>NOTE:</B> This implementation does not check if edge already exists.
     *
     * @param edge edge with [src, dest] already.
     * @return true (since edge always added).
     */
    public boolean addEdge(EdgeBase edge) {
        VertexBase src = edge.getSrc(), dest = edge.getDest();
        src.addOutGoingEdge(edge);
        dest.setIncomingEdge(edge);
        //src can no longer be leaf
        if (hasLeaf(src)) {
            m_leafs.remove(src);
        }
        if (dest.isLeaf()) {
            addLeaf(dest);
        }
        return true;
    }

    private boolean hasLeaf(VertexBase v) {
        return (null != m_leafs) && m_leafs.contains(v);
    }

    public Collection<VertexBase> getLeafs() {
        return (null != m_leafs) ? m_leafs : null;
    }

    private final VertexBase m_root;

    private Set<VertexBase> m_leafs;

    @Override
    public String toString() {
        StringBuilder lstr = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        depthFirst(lstr, sb, m_root);
        return lstr.toString();
    }

    public static final String NL = System.lineSeparator();

    public static void depthFirst(StringBuilder lstr, StringBuilder sb, VertexBase node) {
        if (null != node) {
            sb.append('(').append(node.getVertexName())
                    .append(')');
            if (0 < node.getOutDegree()) {
                String enm;
                VertexBase dest;
                for (EdgeBase edge : node.getOutGoingEdges()) {
                    StringBuilder nsb = new StringBuilder(sb);
                    enm = edge.getEdgeName();
                    nsb.append('-').append(enm).append("->");
                    dest = edge.getDest();
                    if (null != dest) {
                        depthFirst(lstr, nsb, dest);
                        if (dest.isLeaf()) {
                            lstr.append(nsb).append(NL);
                        }
                    }
                }
            }
        }
    }

    /**
     * Test if 2 subgraphs are isomorphic.
     *
     * @param g1 root of graph 1.
     * @param g2 root of graph 2.
     * @return true if g1 and g2 isomorphic. Incoming edges are not considered
     * part of g1 nor g2.
     */
    public static boolean isomorphic(VertexBase g1, VertexBase g2) {
        assert (null != g1) && (null != g2);
        if (0 != g1.compare(g1, g2)) {
            return false;
        }
        if (g1.getOutDegree() != g2.getOutDegree()) {
            return false;
        }
        if (0 == g1.getOutDegree()) {
            return true;
        }
        //use 1st one to use as comparator
        EdgeBase comp = g1.getOutGoingEdges().get(0);
        EdgeBase g1SortedEdges[] = Util.sort(g1.getOutGoingEdges(), comp),
                g2SortedEdges[] = Util.sort(g2.getOutGoingEdges(), comp);
        assert (g1SortedEdges.length == g2SortedEdges.length);
        //At this point we have same number of sorted edges.
        //We'll process in order and keep their dests for next round
        Stack<VertexBase> g1Vx = new Stack<>(),
                g2Vx = new Stack<>();
        for (int i = 0; i < g1SortedEdges.length; i++) {
            if (!g1SortedEdges[i].equals(g2SortedEdges[i])) {
                return false;
            }
            //get dests
            g1 = g1SortedEdges[i].getDest();
            g2 = g2SortedEdges[i].getDest();
            boolean g1Null = (null == g1), g2Null = (null == g2);
            if (g1Null && g2Null) {
                continue;
            }
            if (g1Null != g2Null) {
                return false;
            }
            g1Vx.push(g1);
            g2Vx.push(g2);
        }
        if (g1Vx.size() != g2Vx.size()) {
            return false;
        }
        while (!g1Vx.empty()) {
            g1 = g1Vx.pop();
            g2 = g2Vx.pop();
            if (!isomorphic(g1, g2)) {
                return false;
            }
        }
        return true;
    }
}
