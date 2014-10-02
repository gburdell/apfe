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
import apfe.maze.runtime.Scanner;
import apfe.maze.runtime.State;
import apfe.maze.runtime.Util;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Stack;

/**
 * A directed graph with marked vertices.
 *
 * @author gburdell
 */
public class Graph {

    /**
     * Create graph rooted at the lexer start position.
     *
     * @param lex root lexer.
     */
    public Graph(Scanner lex) {
        this(new State(lex, 0));
    }

    /**
     * Create graph rooted at specified state.
     *
     * @param st root state.
     */
    public Graph(State st) {
        this(new Vertex(st));
    }

    public boolean addEdge(Vertex src, Vertex dest, Acceptor edge) {
        Edge realEdge = new Edge(edge);
        return addEdge(src, realEdge, dest);
    }

    /**
     * Create graph which is rooted at specified vertex.
     *
     * @param root root vertex.
     */
    public Graph(Vertex root) {
        m_root = root;
        if (m_root.isLeaf()) {
            addLeaf(m_root);
        }
    }

    private void addLeaf(Vertex vx) {
        assert vx.isLeaf();
        EMark mark = getMark(vx);
        if (mark == null) {
            mark = EMark.eLeaf();
        } else {
            mark = mark.setLeaf();
        }
        addMark(vx, mark);
    }

    public void addEpsilon(Vertex vx) {
        EMark mark = getMark(vx);
        if (mark == null) {
            mark = EMark.eEpsilon();
        } else {
            mark = mark.setEpsilon();
        }
        addMark(vx, mark);
    }

    public Vertex getRoot() {
        return m_root;
    }

    public boolean isEmpty() {
        return (null == getRoot())
                || ((1 > getRoot().getInDegree()) && (1 > getRoot().getOutDegree()));
    }

    public int addMarks(MarkSet marks) {
        if ((null != marks) && (!marks.isEmpty())) {
            if (null == m_marks) {
                m_marks = new MarkSet();
            }
            m_marks.putAll(marks);
        }
        return markCnt();
    }

    private void addMark(Vertex vx, EMark mark) {
        if (null == m_marks) {
            m_marks = new MarkSet();
        }
        m_marks.put(vx, mark);
    }

    public int markCnt() {
        return (null != m_marks) ? m_marks.size() : 0;
    }

    /**
     * Create edge (unconditionally) in existing graph.
     *
     * @param edge edge with [src, dest] already.
     */
    private void addEdge(Edge edge) {
        Vertex src = edge.getSrc(), dest = edge.getDest();
        EMark mark = getMark(src);
        if (null != mark) {
            if (src.isLeaf()) {
                //assert mark.isLeaf();
                resetLeaf(src);
            } else {
                assert mark.isEpsilon();
                assert !mark.isLeaf();
                removeMark(src);
            }
        }
        src.addOutGoingEdge(edge);
        dest.setIncomingEdge(edge);
        if (dest.isLeaf()) {
            addLeaf(dest);
        }
    }

    /**
     * Add edge to this graph iff. it does not already exist.
     *
     * @param src source vertex.
     * @param edge edge from src to dest.
     * @param dest destination vertex.
     * @return true if this edge is added; else false since edge already exists.
     */
    public boolean addEdge(Vertex src, Edge edge, Vertex dest) {
        if (false) {    //skip for now.
            if (0 < src.getOutDegree()) {
                //look for any matching edges
                for (Edge existingEdge : src.getOutGoingEdges()) {
                    if (existingEdge.equals(edge)) {
                        //if match: then grab dest vertex
                        Vertex existingDest = existingEdge.getDest();
                        assert 1 == existingDest.getInDegree();
                        if (isomorphic(existingDest, dest)) {
                            return false;
                        }
                    }
                }
            } //else: src is a leaf.
        }
        addEdge(edge.addVertices(src, dest));
        return true;
    }

    public boolean isEpsilonMark(Vertex vx) {
        return hasMark(vx) && getMark(vx).isEpsilon();
    }

    private EMark getMark(Vertex vx) {
        return hasMarks() ? m_marks.get(vx) : null;
    }

    public void removeMark(Vertex vx) {
        if (hasMark(vx)) {
            m_marks.remove(vx);
        }
    }

    private boolean hasMark(Vertex v) {
        return hasMarks() && m_marks.containsKey(v);
    }

    public MarkSet getMarks() {
        return hasMarks() ? m_marks : null;
    }

    public Collection<Vertex> getMarkedVertices() {
        return hasMarks() ? getMarks().keySet() : null;
    }

    private boolean hasMarks() {
        return (null != m_marks) && !m_marks.isEmpty();
    }

    private void resetLeaf(Vertex vx) {
        EMark mark = getMark(vx).resetLeaf();
        if (null == mark) {
            m_marks.remove(vx);
        } else {
            addMark(vx, mark);
        }
    }

    private final Vertex m_root;

    public static enum EMark {

        LEAF, EPSILON, BOTH;

        public static EMark eLeaf() {
            return LEAF;
        }

        public static EMark eEpsilon() {
            return EPSILON;
        }

        public boolean isLeaf() {
            return equals(LEAF) || equals(BOTH);
        }

        public boolean isEpsilon() {
            return equals(EPSILON) || equals(BOTH);
        }

        public EMark setLeaf() {
            return equals(EPSILON) ? BOTH : LEAF;
        }

        public EMark setEpsilon() {
            return equals(LEAF) ? BOTH : EPSILON;
        }

        public EMark resetLeaf() {
            //assert isLeaf();
            return isEpsilon() ? EPSILON : null;
        }

        public EMark resetEpsilon() {
            assert isEpsilon() && !isLeaf();
            return null;
        }
    }

    public static class MarkSet extends HashMap<Vertex, EMark> {
    }

    /**
     * Map of Vertex to mark type. Use of map here may be superfluous: but helps
     * debugging for now.
     */
    private MarkSet m_marks;

    @Override
    public String toString() {
        StringBuilder lstr = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        depthFirst(lstr, sb, m_root, this);
        return lstr.toString();
    }

    public static final String NL = System.lineSeparator();

    public static void depthFirst(StringBuilder lstr, StringBuilder sb, Vertex node) {
        depthFirst(lstr, sb, node, null);
    }

    public static void depthFirst(StringBuilder lstr, StringBuilder sb, Vertex node,
            Graph g) {
        if (null != node) {
            sb.append('(').append(node.getVertexName());
            if (null != g) {
                EMark mark = g.getMark(node);
                if (null != mark) {
                    if (mark.isEpsilon()) {
                        sb.append("?");
                    }
                    if (mark.isLeaf()) {
                        sb.append(".");
                    }
                }
            }
            //sb.append("-0x").append(Integer.toString(node.hashCode(), 16));
            sb.append(')');
            if (0 < node.getOutDegree()) {
                String enm;
                Vertex dest;
                for (Edge edge : node.getOutGoingEdges()) {
                    StringBuilder nsb = new StringBuilder(sb);
                    enm = edge.getEdgeName();
                    nsb.append('-').append(enm).append("->");
                    dest = edge.getDest();
                    if (null != dest) {
                        depthFirst(lstr, nsb, dest, g);
                        if (dest.isLeaf()) {
                            lstr.append(nsb).append(NL);
                        }
                    }
                }
            }
        }
    }

    /**
     * ***********************************************
     * TODO: consider epsilon marks!!
     * *********************************************** 
     * Test if 2 subgraphs are
     * isomorphic.
     *
     * @param g1 root of graph 1.
     * @param g2 root of graph 2.
     * @return true if g1 and g2 isomorphic. Incoming edges are not considered
     * part of g1 nor g2.
     */
    public static boolean isomorphic(Vertex g1, Vertex g2) {
        assert (null != g1) && (null != g2);
        final Comparator<Vertex> vcomp = g1.getComparator();
        if (0 != g1.getComparator().compare(g1, g2)) {
            return false;
        }
        if (g1.getOutDegree() != g2.getOutDegree()) {
            return false;
        }
        if (0 == g1.getOutDegree()) {
            return true;
        }
        //use 1st one as comparator
        final Comparator<Edge> ecomp = g1.getOutGoingEdges().get(0).getComparator();
        Edge g1SortedEdges[] = Util.sort(Edge.class, g1.getOutGoingEdges(), ecomp),
                g2SortedEdges[] = Util.sort(Edge.class, g2.getOutGoingEdges(), ecomp);
        assert (g1SortedEdges.length == g2SortedEdges.length);
        //At this point we have same number of sorted edges.
        //We'll process in order and keep their dests for next round
        Stack<Vertex> g1Vx = new Stack<>(),
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
