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
package apfe.dsl.vlogpp.parser;

import apfe.runtime.Acceptor;
import apfe.runtime.Marker;
import apfe.runtime.CharClass;
import apfe.runtime.CharSeq;
import apfe.runtime.EndOfFile;
import apfe.runtime.EndOfLine;
import apfe.runtime.ICharClass;
import apfe.runtime.Memoize;
import apfe.runtime.NotPredicate;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;

/**
 *
 * @author gburdell
 */
public class Comment extends Acceptor {

    @Override
    protected boolean accepti() {
        // Comment <- SL_COMMENT / ML_COMMENT
        PrioritizedChoice pc = new PrioritizedChoice(new PrioritizedChoice.Choices() {
            @Override
            public Acceptor getChoice(int ix) {
                Acceptor a = null;
                switch (ix) {
                    case 0:
                        a = new SL_COMMENT();
                        break;
                    case 1:
                        a = new ML_COMMENT();
                        break;
                };
                return a;
            }
        });
        boolean match = (null != (pc = match(pc)));
        if (match) {
            m_comment = super.toString();
        }
        return match;
    }

    private String m_comment;

    @Override
    public String toString() {
        return m_comment;
    }

    @Override
    public Acceptor create() {
        return new Comment();
    }

    @Override
    protected void memoize(Marker mark, Marker endMark) {
        stMemo.add(mark, this, endMark);
    }

    @Override
    protected Memoize.Data hasMemoized(Marker mark) {
        return stMemo.memoized(mark);
    }

    /**
     * Memoize for all instances of Comment.
     */
    private static Memoize stMemo = new Memoize();

    public static class SL_COMMENT extends Acceptor {

        private static PrioritizedChoice getAlt() {
            return new PrioritizedChoice(new PrioritizedChoice.Choices() {
                @Override
                public Acceptor getChoice(int ix) {
                    Acceptor a = null;
                    switch (ix) {
                        case 0:
                            a = new EndOfLine();
                            break;
                        case 1:
                            a = new EndOfFile();
                            break;
                    };
                    return a;
                }
            });
        }

        @Override
        protected boolean accepti() {
            //"//" (!(EOL / EOF) .)* (EOL / EOF)
            Sequence s1 = new Sequence(new NotPredicate(getAlt()),
                    new CharClass(ICharClass.IS_ANY));
            Repetition r1 = new Repetition(s1, Repetition.ERepeat.eZeroOrMore);
            Sequence s2 = new Sequence(new CharSeq("//"), r1, getAlt());
            boolean match = (null != (s2 = match(s2)));
            if (match) {
                m_comment = super.toString();
            }
            return match;
        }

        private String m_comment;

        @Override
        public String toString() {
            return m_comment;
        }

        @Override
        public Acceptor create() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        protected void memoize(Marker mark, Marker endMark) {
            stMemo.add(mark, this, endMark);
        }

        @Override
        protected Memoize.Data hasMemoized(Marker mark) {
            return stMemo.memoized(mark);
        }

        /**
         * Memoize for all instances of SL_COMMENT.
         */
        private static Memoize stMemo = new Memoize();

    }

    public static class ML_COMMENT extends Acceptor {

        @Override
        protected boolean accepti() {
            //"/*" (!("*/" / EOF) .)* "*/"
            PrioritizedChoice pc = new PrioritizedChoice(new PrioritizedChoice.Choices() {
                @Override
                public Acceptor getChoice(int ix) {
                    Acceptor a = null;
                    switch (ix) {
                        case 0:
                            a = new CharSeq("*/");
                            break;
                        case 1:
                            a = new EndOfFile();
                            break;
                    };
                    return a;
                }
            });

            Sequence s1 = new Sequence(new NotPredicate(pc),
                    new CharClass(ICharClass.IS_ANY));
            Repetition r1 = new Repetition(s1, Repetition.ERepeat.eZeroOrMore);
            Sequence s2 = new Sequence(new CharSeq("/*"), r1, new CharSeq("*/"));
            boolean match = (null != (s2 = match(s2)));
            if (match) {
                m_comment = super.toString();
            }
            return match;
        }

        private String m_comment;

        @Override
        public String toString() {
            return m_comment;
        }

        @Override
        public Acceptor create() {
            return new ML_COMMENT();
        }

        @Override
        protected void memoize(Marker mark, Marker endMark) {
            stMemo.add(mark, this, endMark);
        }

        @Override
        protected Memoize.Data hasMemoized(Marker mark) {
            return stMemo.memoized(mark);
        }

        /**
         * Memoize for all instances of ML_COMMENT.
         */
        private static Memoize stMemo = new Memoize();

    }
}
