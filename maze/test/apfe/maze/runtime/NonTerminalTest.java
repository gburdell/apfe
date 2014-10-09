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
public class NonTerminalTest {
    public static class A_Term extends Terminal {

        @Override
        protected int getTokCode() {
            return stCode;
        }

        public static final int stCode = 1;
        public static final A_Term stTheOne = new A_Term();
    }

    public static class B_Term extends Terminal {

        @Override
        protected int getTokCode() {
            return stCode;
        }

        public static final int stCode = 2;
        public static final B_Term stTheOne = new B_Term();
    }

    public static class C_Term extends Terminal {

        @Override
        protected int getTokCode() {
            return stCode;
        }

        public static final int stCode = 3;
        public static final C_Term stTheOne = new C_Term();
    }

    public static class Grammar extends NonTerminal {

        private Grammar(Acceptor nonTerm) {
            super(nonTerm);
        }
        public static final Grammar stTheOne;

        static {
            stTheOne = new Grammar(
                    new Sequence(A_Term.stTheOne, B_Term.stTheOne, C_Term.stTheOne)
            );
        }
    }

    public static class ScannerTest extends Scanner {

        public ScannerTest() {
            add(Token.create("A", A_Term.stCode));
            add(Token.create("B", A_Term.stCode));
            add(Token.create("C", A_Term.stCode));
            //add(Token.create("<EOF>", Token.EOF));
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

    /**
     * Test of accept method, of class NonTerminal.
     */
    @Test
    public void testAccept() {
        System.out.println("accept");
        Scanner scanner = new ScannerTest();
        int n = RunMaze.run(scanner, Grammar.stTheOne);
        assertTrue(0 < n);
    }

}
