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

import org.junit.BeforeClass;
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

    public static class PortDecl extends Acceptor {

        @Override
        public Acceptor create() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        protected Graph acceptImpl() {
            Sequence s1 = new Sequence(new Terminal(LPAREN), new Terminal(IDENT),
                    new Terminal(COMMA), new Terminal(IDENT), new Terminal(RPAREN));
            Graph subg = s1.accept(getSubgraph());
            return subg;
        }

    }

    public static class Start extends Acceptor {
        public Start(Scanner lex) {
            m_lex = lex;
        }
        
        public Graph run() {
            Graph g = new Graph(m_lex);
            g = accept(g);
            return g;
        }
        
        private final Scanner   m_lex;
        
        @Override
        public Acceptor create() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        protected Graph acceptImpl() {
            Sequence s1 = new Sequence(new Terminal(MODULE), new Terminal(IDENT),
                    new PortDecl(), new Terminal(SEMI));
            Graph subg = s1.accept(getSubgraph());
            return subg;
        }

    }

    public static final Scanner stScanner = new MyScanner();

    /**
     * Test of acceptImpl method, of class Sequence.
     */
    @Test
    public void testAcceptImpl() {
        System.out.println("acceptImpl");
        Start moduleDefn = new Start(stScanner);
        Graph result = moduleDefn.run();
        assertNotNull(result);
    }

}
