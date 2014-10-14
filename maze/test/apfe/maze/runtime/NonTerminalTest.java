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

    public static class D_Term extends Terminal {

        @Override
        protected int getTokCode() {
            return stCode;
        }

        public static final int stCode = 4;
        public static final D_Term stTheOne = new D_Term();
    }

    public static class Grammar extends NonTerminal {

        private Grammar(Acceptor nonTerm) {
            super(nonTerm);
        }
        public static final Grammar stTheOne;

        static {
            stTheOne = new Grammar(
                    new Sequence(A_Term.stTheOne, B_Term.stTheOne, C_Term.stTheOne, 
                    new Alternates(new Repetition(A_Term.stTheOne, true),
                            new Repetition(B_Term.stTheOne)))
            );
        }
    }

    public static class Grammar2 extends NonTerminal {

        private Grammar2(Acceptor nonTerm) {
            super(nonTerm);
        }
        public static final Grammar2 stTheOne;

        static {
            stTheOne = new Grammar2(
                    new Sequence(A_Term.stTheOne,
                            new Optional(B_Term.stTheOne), C_Term.stTheOne)
            );
        }
    }

    /*
     source_text : 
     a b c d
     EOF
     ;
     d: e*

     */
    public static class Grammar3 extends NonTerminal {

        private static class a extends NonTerminal {

            //a: A_K? A_K A_K ;
            public a() {
                super(new Sequence(new Optional(A_Term.stTheOne),
                        A_Term.stTheOne, A_Term.stTheOne));
            }

        }

        private static class b extends NonTerminal {

            //     b: B_K? ;
            public b() {
                super(new Optional(B_Term.stTheOne));
            }

        }

        private static class c extends NonTerminal {

            //c: C_K*;
            public c() {
                super(new Repetition(C_Term.stTheOne));
            }

        }

        private static class d extends NonTerminal {

            //d: e*;
            public d() {
                super(new Repetition(new e()));
            }

        }

        private static class e extends NonTerminal {

            //     e: A_K+ | B_K* | C_K? | D_K+ ;
            public e() {
                super(stAlts);
            }

            private static final Acceptor a1, a2, a3, a4;
            private static final Alternates stAlts;

            static {
                a1 = new Repetition(A_Term.stTheOne, true);
                a2 = new Repetition(B_Term.stTheOne);
                a3 = new Optional(C_Term.stTheOne);
                a4 = new Repetition(D_Term.stTheOne, true);
                stAlts = new Alternates(a1, a2, a3, a4);
            }

        }

        private Grammar3(Acceptor nonTerm) {
            super(nonTerm);
        }
        public static final Grammar3 stTheOne;

        static {
            stTheOne = new Grammar3(
                    new Sequence(new a(), new b(), new c(), new d(),
                            Terminal.EOF.OaO())
            );
        }
    }

    public static class ScannerTest2 extends Scanner {

        public ScannerTest2() {
            //AAACCAABBDDAA
            add(Token.create("A", A_Term.stCode));
            add(Token.create("A", A_Term.stCode));
            add(Token.create("A", A_Term.stCode));
            add(Token.create("C", C_Term.stCode));
            add(Token.create("C", C_Term.stCode));
            add(Token.create("A", A_Term.stCode));
            add(Token.create("A", A_Term.stCode));
            add(Token.create("B", B_Term.stCode));
            add(Token.create("B", B_Term.stCode));
            add(Token.create("D", D_Term.stCode));
            add(Token.create("D", D_Term.stCode));
            add(Token.create("A", A_Term.stCode));
            add(Token.create("A", A_Term.stCode));
            add(Token.create("<EOF>", Token.EOF));
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

    public static class ScannerTest extends Scanner {

        public ScannerTest() {
            add(Token.create("A", A_Term.stCode));
            add(Token.create("B", B_Term.stCode));
            add(Token.create("C", C_Term.stCode));
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
        {
            Scanner scanner = new ScannerTest();
            RatsNest paths = RunMaze.runMaze(scanner, Grammar.stTheOne).getDone();
            assertFalse(paths.isEmpty());
        }
        {
            Scanner scanner = new ScannerTest();
            int n = RunMaze.runMaze(scanner, Grammar2.stTheOne).getDone().size();
            assertTrue(0 < n);
        }
        {
            Scanner scanner = new ScannerTest2();
            RatsNest paths = RunMaze.runMaze(scanner, Grammar3.stTheOne).getDone();
            assertFalse(paths.isEmpty());
        }
    }

}
