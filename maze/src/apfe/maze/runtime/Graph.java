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

import apfe.maze.runtime.graph.DiGraph;
import apfe.maze.runtime.graph.Edge;
import apfe.maze.runtime.graph.Vertex;

/**
 *
 * @author gburdell
 */
public class Graph extends DiGraph<State, Acceptor> {

    public Graph(Scanner lex) {
        this(new State(lex, 0));
    }

    public Graph(State st) {
        super(st);
    }

    public Graph(Vertex<State,Acceptor> st) {
        super(st);
    }

    /**
     * Create edge in existing graph.
     * Add dest as leaf vertex, if it is a leaf node.
     * <B>NOTE:</B> This implementation only checks if -edge>(dest) exists.
     * It does not check if subtree rooted by edge exists.  It just checks
     * one-level deep.
     * @param src source vertex.
     * @param dest dest vertex
     * @param edge edge connecting src to dest.
     * @return true if edge was added.
     */
    @Override
    public boolean addEdge(Vertex<State,Acceptor> src, Vertex<State,Acceptor> dest, Acceptor edge) {
        boolean add = (1 > src.getOutDegree());
        if (false) {//(!add) {
            Acceptor ea;
            for (Edge<State,Acceptor> exists : src.getOutGoingEdges()) {
                //dont add if we already have edge+dest
                add = !(exists.getData().equals(edge) 
                        && exists.getDest().getData().equals(dest.getData()));
                if (!add) {
                    //curious: if this is the case
                    assert (exists.getDest().getOutDegree() == dest.getOutDegree());
                    //assert dest.isLeaf(); //curious??
                    return false;
                }
            }
        }
        return super.addEdge(src, dest, edge);
    }

    static {
        stPrintEdgeName = new IPrintEdgeName<State, Acceptor>() {
            @Override
            public String getEdgeName(Edge<State, Acceptor> e) {
                String s;
                if (e.getData() instanceof Terminal) {
                    s = "'" + ((Terminal) e.getData()).getMatched().getText() + "'";
                } else {
                    s = e.getData().getClass().getSimpleName();
                }
                return s;
            }
        };
        stPrintVertexName = new IPrintVertexName<State,Acceptor>() {
            @Override
            public String getVertexName(Vertex<State,Acceptor> v) {
                return Integer.toString(v.getData().getPos());
            }
        };
    }
}
