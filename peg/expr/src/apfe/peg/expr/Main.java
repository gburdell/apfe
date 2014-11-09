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
package apfe.peg.expr;

import apfe.peg.expr.generated.*;
import apfe.runtime.Acceptor;
import apfe.runtime.CharBufState;
import apfe.runtime.CharBuffer;
import apfe.runtime.ParseError;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.State;
import apfe.runtime.Util;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class Main {

    public static void main(String argv[]) {
        Expr instance;
        CharBufState st;
        for (String s : argv) {
            State.clear();
            CharBuffer buf = new CharBuffer("<test>", s);
            st = CharBufState.create(buf);
            instance = new Expr();
            boolean ok = instance.acceptTrue();
            if (ok) {
                ExprNode ast = ExprNode.create(instance);
                int val = ast.evaluate();
                System.out.println("test: " + s + " returns: " + ast.toString() + " = " + val);
            } else {
                ParseError.printTopMessage();
            }
        }

    }

    public static interface Node {

        public int evaluate();

        @Override
        public String toString();
    }

    /*
     Expr <- Expr ('+' / '-') Term
     / Term
     */
    public static class ExprNode implements Node {

        public static ExprNode create(Expr acc) {
            ExprNode root;
            List<Acceptor> items = acc.getItems();
            assert (null != items);
            root = new ExprNode(items.get(0));
            if (1 < items.size()) {
                //flatten to left associative
                //Term [op Term]+
                items.remove(0);
                //remains: sequence of [op Term]
                for (Acceptor ele : items) {
                    Sequence opTerm = Util.<Sequence>downCast(ele);
                    root = new ExprNode(root, opTerm);
                }
            }
            return root;
        }

        private ExprNode(Acceptor lhs) {
            m_rhs = TermNode.create(Util.<Term>downCast(lhs));
        }

        private ExprNode(ExprNode lhs, Sequence ele) {
            m_lhs = lhs;
            Acceptor opTerm[] = Util.<Sequence>downCast(ele).getAccepted();
            assert 2 == opTerm.length;
            m_op = getOp(opTerm[0]);
            Term term = Util.downCast(opTerm[1]);
            m_rhs = TermNode.create(term);
        }

        @Override
        public int evaluate() {
            int rhs = m_rhs.evaluate();
            if (null == m_lhs) {
                return rhs;
            }
            int val = m_lhs.evaluate();
            if (null != m_op) {
                val = (EOp.eAdd == m_op) ? (val + rhs) : (val - rhs);
            }
            return val;
        }

        private static enum EOp {

            eAdd, eSub;
        };

        private static EOp getOp(Acceptor op) {
            return op.toString().equals("+") ? EOp.eAdd : EOp.eSub;
        }

        private static String toString(EOp op) {
            return (EOp.eAdd == op) ? "+" : "-";
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (null != m_lhs) {
                sb.append(m_lhs.toString());
                if (null != m_op) {
                    sb.append(toString(m_op));
                }
            }
            sb.append(m_rhs.toString());
            return sb.toString();
        }
        private EOp m_op;
        private ExprNode m_lhs;
        private TermNode m_rhs;
    }

    /*
     Term <- Term [* /] Factor
     / Factor
     */
    public static class TermNode implements Node {

        @Override
        public int evaluate() {
            int rhs = m_rhs.evaluate();
            if (null == m_lhs) {
                return rhs;
            }
            int val = m_lhs.evaluate();
            if (null != m_op) {
                val = (EOp.eMult == m_op) ? (val * rhs) : (val / rhs);
            }
            return val;
        }

        public static TermNode create(Term acc) {
            TermNode root;
            List<Acceptor> items = acc.getItems();
            assert (null != items);
            root = new TermNode(items.get(0));
            if (1 < items.size()) {
                //flatten to left associative
                //Factor [op Factor]+
                items.remove(0);
                //remains: sequence of [op Factor]
                for (Acceptor ele : items) {
                    Sequence opFactor = Util.<Sequence>downCast(ele);
                    root = new TermNode(root, opFactor);
                }
            }
            return root;
        }

        private TermNode(Acceptor lhs) {
            m_rhs = new FactorNode(Util.<Factor>downCast(lhs));
        }

        private TermNode(TermNode lhs, Sequence ele) {
            m_lhs = lhs;
            Acceptor opFactor[] = Util.<Sequence>downCast(ele).getAccepted();
            assert 2 == opFactor.length;
            m_op = getOp(opFactor[0]);
            Factor factor = Util.downCast(opFactor[1]);
            m_rhs = new FactorNode(factor);
        }

        private static enum EOp {

            eMult, eDiv
        };

        private static EOp getOp(Acceptor op) {
            return op.toString().equals("*") ? EOp.eMult : EOp.eDiv;
        }

        private static String toString(EOp op) {
            return (EOp.eMult == op) ? "*" : "/";
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (null != m_lhs) {
                sb.append(m_lhs.toString());
                if (null != m_op) {
                    sb.append(toString(m_op));
                }
            }
            sb.append(m_rhs.toString());
            return sb.toString();
        }

        private EOp m_op;
        private TermNode m_lhs;
        private FactorNode m_rhs;
    }

    /*
     Factor <- Unary? '(' Expr ')'
     /  Unary? [0-9]+
     /  Unary? [a-z]+
     */
    public static class FactorNode implements Node {

        public FactorNode(Factor acc) {
            PrioritizedChoice pc = Util.downCast(acc.getAccepted());
            Acceptor items[] = Util.<Sequence>downCast(pc.getAccepted()).getAccepted();
            Repetition rep = Util.downCast(items[0]);
            if (0 < rep.sizeofAccepted()) {
                m_unaryOp = (rep.toString().charAt(0) == '-') ? EUnary.eMinus : EUnary.ePlus;
            }
            switch (pc.whichAccepted()) {
                case 0: //(expr)
                    m_ele = ExprNode.create(Util.<Expr>downCast(items[2]));
                    break;
                case 1: //number
                    rep = Util.downCast(items[1]);
                    m_ele = new NumberNode(rep);
                    break;
                case 2: //ident
                //fall through: not supported
                default:
                    assert false;
            }
        }

        @Override
        public int evaluate() {
            int rval = m_ele.evaluate();
            if ((null != m_unaryOp) && (EUnary.eMinus == m_unaryOp)) {
                rval = -rval;
            }
            return rval;
        }

        private static enum EUnary {

            ePlus, eMinus
        };

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (null != m_unaryOp) {
                sb.append(toString(m_unaryOp));
            }
            final boolean isExpr = (m_ele instanceof ExprNode);
            if (isExpr) {
                sb.append('(');
            }
            sb.append(m_ele.toString());
            if (isExpr) {
                sb.append(')');
            }
            return sb.toString();
        }

        private static String toString(EUnary op) {
            return (EUnary.ePlus == op) ? "+" : "-";
        }

        public static class NumberNode implements Node {

            public NumberNode(Acceptor acc) {
                m_number = new Integer(acc.toString());
            }

            @Override
            public String toString() {
                return m_number.toString();
            }

            @Override
            public int evaluate() {
                return m_number;
            }

            private Integer m_number;
        }

        //unary operator
        private EUnary m_unaryOp;
        //element value: Expr, number or ident
        //NOTE: ident not supported yet!
        private Node m_ele;
    }

}
