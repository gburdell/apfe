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

    public Graph(Vertex<State> st) {
        super(st);
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
        stPrintVertexName = new IPrintVertexName<State>() {
            @Override
            public String getVertexName(Vertex<State> v) {
                return "";
            }
        };
    }
}
