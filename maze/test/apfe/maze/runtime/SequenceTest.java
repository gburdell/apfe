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

    public static final int MODULE = 1;
    public static final int IDENT = 2;
    public static final int LPAREN = 3;
    public static final int RPAREN = 4;
    public static final int SEMI = 5;
    public static final int COMMA = 6;
    public static final int EOF = Token.EOF;

    public static class MyScanner extends Scanner {

        public MyScanner() {
            super.add(create("module", MODULE));
            super.add(create("mod1", IDENT));
            super.add(create("(", LPAREN));
            super.add(create("p1", IDENT));
            super.add(create(",", COMMA));
            super.add(create("p2", IDENT));
            super.add(create(",", COMMA));
            super.add(create("p3", IDENT));
            super.add(create(",", COMMA));
            super.add(create("p4", IDENT));
            super.add(create(")", RPAREN));
            super.add(create(";", SEMI));
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

    public static class PortDecl extends Acceptor implements NonTerminal {

        @Override
        public Acceptor create() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        protected boolean acceptImpl() {
            Acceptor acc1;
            Repetition r1 = new Repetition(new Sequence(new Terminal(COMMA), new Terminal(IDENT)));
            Sequence s2 = new Sequence(new Terminal(LPAREN), new Terminal(IDENT),
                    //new Terminal(COMMA), new Terminal(IDENT), new Terminal(RPAREN));
                    r1, new Terminal(RPAREN));
            if (true) {
                acc1 = s2;
            } else {
                //TODO: need to test this after above works.
                //And then add stale branch/path removal.
                Sequence s1 = new Sequence(new Terminal(LPAREN), new Terminal(RPAREN));
                Sequence s3 = new Sequence(new Optional(new Terminal(LPAREN)),
                        new Optional(new Terminal(IDENT)), new Optional(new Terminal(RPAREN)));
                Alternates a1 = new Alternates(s1, s2, s3);
                acc1 = a1;
            }
            Graph subg = acc1.accept(getSubgraphRoot());
            boolean ok = (null != subg);
            if (ok) {
                //addEdge(getSubgraphRoot(), this, subg);
                setSubGraph(subg);
            }
            return ok;
        }

    }

    public static class ModuleDefn extends Acceptor implements NonTerminal {

        public ModuleDefn(Scanner lex) {
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
            Sequence s1 = new Sequence(new Terminal(MODULE), new Terminal(IDENT),
                    new PortDecl(), new Terminal(SEMI));
            Graph subg = s1.accept(getSubgraph().getRoot());
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
        ModuleDefn moduleDefn = new ModuleDefn(stScanner);
        Graph result = moduleDefn.run();
        System.out.println(result.toString());
        assertNotNull(result);
    }

}
