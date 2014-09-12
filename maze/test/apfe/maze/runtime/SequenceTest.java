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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gburdell
 */
public class SequenceTest {

    public static final int A = 1;
    public static final int B = 2;
    public static final int C = 3;
    public static final int D = 4;
    public static final int SEMI = 5;
    public static final int COMMA = 6;
    public static final int EOF = Token.EOF;

    public static class MyScanner extends Scanner {

        public MyScanner() {
            super.add(create("A", A));
            super.add(create("A", A));
            super.add(create("A", A));
            super.add(create("A", A));
            super.add(create("A", A));
            super.add(create("<EOF>", EOF));
        }

        private static Token create(String text, int code) {
            return Token.create(text, code);
        }

        @Override
        public boolean isEOF() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Token nextToken() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    public static class Grammar extends Acceptor implements NonTerminal {

        public Grammar(Scanner lex) {
            m_lex = lex;
        }

        public Graph run() {
            Graph g = new Graph(m_lex);
            g = accept(g.getRoot());
            return g;
        }

        private final Scanner m_lex;

        @Override
        public Acceptor create() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        protected boolean acceptImpl() {
            // A pathological tc (B...B)? A+
            Sequence pathological;
            {
                final int n = 25;
                Acceptor bt[] = new Acceptor[n];
                for (int i = 0; i < n;) {
                    bt[i++] = new Optional(new Terminal(B));
                }
                pathological = new Sequence(new Sequence(bt),
                        new Repetition(new Terminal(A), true));
            }

            Alternates a1 = new Alternates(
                    /*
                    new Repetition(new Terminal(A)),
                    new Sequence(new Terminal(A), new Repetition(new Sequence(new Terminal(A), new Optional(new Terminal(D))))),
                    //a sequence which fails after AA
                    new Sequence(new Terminal(A), new Terminal(A), new Optional(new Terminal(C)), new Optional(new Terminal(D))),
                    new Sequence(new Terminal(A), new Repetition(new Terminal(A), true)),
                    new Sequence(new Terminal(A), new Repetition(new Terminal(A), true), new Optional(new Terminal(C))),
                    new Sequence(new Terminal(A), new Repetition(new Terminal(A), true), new Terminal(C)),
                    new Sequence(new Optional(new Terminal(B)), new Repetition(new Terminal(A), true))
                    ,
                    */
                    pathological
            );
            Graph subg = a1.accept(getSubgraph().getRoot());
            boolean ok = (null != subg);
            if (ok) {
                //addEdge(getSubgraphRoot(), this, subg);
                setSubGraph(subg);
            }
            return ok;
        }

    }

    public static final Scanner stScanner = new MyScanner();

    /**
     * Test of acceptImpl method, of class Sequence.
     */
    @Test
    public void testAcceptImpl() {
        System.out.println("acceptImpl");
        Grammar gram = new Grammar(stScanner);
        Graph result = gram.run();
        System.out.println(result.toString());
        assertNotNull(result);
    }

}
