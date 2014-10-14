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

        public static Terminal OaO() {
            return stTheOne;
        }
        private static final int stCode = 1;
        private static final A_Term stTheOne = new A_Term();
    }

    public static class B_Term extends Terminal {

        @Override
        protected int getTokCode() {
            return stCode;
        }

        public static Terminal OaO() {
            return stTheOne;
        }

        private static final int stCode = 2;
        private static final B_Term stTheOne = new B_Term();
    }

    public static class C_Term extends Terminal {

        @Override
        protected int getTokCode() {
            return stCode;
        }

        public static Terminal OaO() {
            return stTheOne;
        }

        private static final int stCode = 3;
        private static final C_Term stTheOne = new C_Term();
    }

    public static class D_Term extends Terminal {

        @Override
        protected int getTokCode() {
            return stCode;
        }

        public static Terminal OaO() {
            return stTheOne;
        }

        private static final int stCode = 4;
        private static final D_Term stTheOne = new D_Term();
    }

    public static class Grammar extends NonTerminal {

        private static final Grammar stTheOne = new Grammar();
        private static Acceptor stAcc;

        public static Grammar OaO() {
            return stTheOne;
        }

        @Override
        public Acceptor getAcceptor() {
            if (null == stAcc) {
                stAcc = new Sequence(A_Term.OaO(), B_Term.OaO(), C_Term.OaO(),
                        new Alternates(new Repetition(A_Term.OaO(), true),
                                new Repetition(B_Term.OaO())));
            }
            return stAcc;
        }

    }

    public static class Grammar2 extends NonTerminal {

        private static final Grammar stTheOne = new Grammar();
        private static Acceptor stAcc;

        public static Grammar OaO() {
            return stTheOne;
        }

        @Override
        public Acceptor getAcceptor() {
            if (null == stAcc) {
                stAcc = new Sequence(A_Term.OaO(),
                        new Optional(B_Term.OaO()), C_Term.OaO());
            }
            return stAcc;
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

        //a: A_K? A_K A_K ;
        private static class a extends NonTerminal {

            private static final a stTheOne = new a();
            private static Acceptor stAcc;

            public static a OaO() {
                return stTheOne;
            }

            @Override
            public Acceptor getAcceptor() {
                if (null == stAcc) {
                    stAcc = new Sequence(new Optional(A_Term.OaO()),
                            A_Term.OaO(), A_Term.OaO());
                }
                return stAcc;
            }
        }

        //     b: B_K? ;
        private static class b extends NonTerminal {

            private static final b stTheOne = new b();
            private static Acceptor stAcc;

            public static b OaO() {
                return stTheOne;
            }

            @Override
            public Acceptor getAcceptor() {
                if (null == stAcc) {
                    stAcc = new Optional(B_Term.OaO());
                }
                return stAcc;
            }
        }

        private static class c extends NonTerminal {

            private static final c stTheOne = new c();
            private static Acceptor stAcc;

            public static c OaO() {
                return stTheOne;
            }

            @Override
            public Acceptor getAcceptor() {
                if (null == stAcc) {
                    stAcc = new Repetition(C_Term.OaO());
                }
                return stAcc;
            }
        }

        //d: e*;
        private static class d extends NonTerminal {

            private static final d stTheOne = new d();
            private static Acceptor stAcc;

            public static d OaO() {
                return stTheOne;
            }

            @Override
            public Acceptor getAcceptor() {
                if (null == stAcc) {
                    stAcc = new Repetition(e.OaO());
                }
                return stAcc;
            }
        }

        //     e: A_K+ | B_K* | C_K? | D_K+ ;
        private static class e extends NonTerminal {

            private static final e stTheOne = new e();
            private static Acceptor stAcc;

            public static e OaO() {
                return stTheOne;
            }

            @Override
            public Acceptor getAcceptor() {
                if (null == stAcc) {
                    final Acceptor a1, a2, a3, a4;
                    a1 = new Repetition(A_Term.OaO(), true);
                    a2 = new Repetition(B_Term.OaO());
                    a3 = new Optional(C_Term.OaO());
                    a4 = new Repetition(D_Term.OaO(), true);
                    stAcc = new Alternates(a1, a2, a3, a4);
                }
                return stAcc;
            }
        }

        private static final Grammar3 stTheOne = new Grammar3();
        private static Acceptor stAcc;

        public static Grammar3 OaO() {
            return stTheOne;
        }
        
        @Override
        public Acceptor getAcceptor() {
            if (null == stAcc) {
                stAcc = new Sequence(a.OaO(), b.OaO(), c.OaO(), d.OaO(),
                Terminal.EOF.OaO());
            }
            return stAcc;
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
