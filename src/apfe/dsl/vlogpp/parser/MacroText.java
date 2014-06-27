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
import apfe.runtime.CharClass;
import apfe.runtime.CharSeq;
import apfe.runtime.EndOfLine;
import apfe.runtime.ICharClass;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;

/**
 *
 * @author gburdell
 */
public class MacroText extends Acceptor {

    @Override
    protected boolean accepti() {
        //MacroText <- MacroText2 EOL
        boolean match = (new MacroText2()).acceptTrue();
        if (match) {
            m_text = super.toString();
            match &= (new EndOfLine()).acceptTrue();
            if (!match) {
                m_text = null;
            }
        }
        return match;
    }

    private String m_text;

    @Override
    public String toString() {
        return m_text;
    }

    @Override
    public Acceptor create() {
        return new MacroText();
    }

    private static class MacroText2 extends Acceptor {
        @Override
        protected boolean accepti() {
            //MacroText2 <- (("`\\`\"") / MacroText3)* ("\\" EOL MacroText2)?
            PrioritizedChoice pc1 = new PrioritizedChoice(new PrioritizedChoice.Choices() {
                @Override
                public Acceptor getChoice(int ix) {
                    Acceptor a = null;
                    switch (ix) {
                        case 0:
                            a = new CharSeq("`\\`\"");
                            break;
                        case 1:
                            a = new MacroText3();
                            break;
                    }
                    return a;
                }
            });
            Repetition r1 = new Repetition(pc1, Repetition.ERepeat.eZeroOrMore);
            Repetition r2 = new Repetition(
                    new Sequence(new CharSeq('\\'), new EndOfLine(), new MacroText2()),
                    Repetition.ERepeat.eOptional);
            Sequence s1 = new Sequence(r1, r2);
            boolean match = (null != (s1 = match(s1)));
            if (match) {
                m_text = super.toString();
            }
            return match;
        }

        private String m_text;

        @Override
        public String toString() {
            return m_text;
        }

        @Override
        public Acceptor create() {
            return new MacroText2();
        }
    }
    private static class MacroText3 extends Acceptor {
        @Override
        protected boolean accepti() {
            //MacroText3 <- Spacing2+ / (NotChar3 .)
            PrioritizedChoice pc1 = new PrioritizedChoice(new PrioritizedChoice.Choices() {
                @Override
                public Acceptor getChoice(int ix) {
                    Acceptor a = null;
                    switch (ix) {
                        case 0:
                            a = new Repetition(new Spacing2(), Repetition.ERepeat.eOneOrMore);
                            break;
                        case 1:
                            a = new Sequence(new NotChar3(), new CharClass(ICharClass.IS_ANY));
                            break;
                    }
                    return a;
                }
            });
            boolean match = (null != (pc1 = match(pc1)));
            if (match) {
                m_text = super.toString();
            }
            return match;
        }

        private String m_text;

        @Override
        public String toString() {
            return m_text;
        }

        @Override
        public Acceptor create() {
            return new MacroText3();
        }
    }
    private static class Spacing2 extends Acceptor {
        @Override
        protected boolean accepti() {
            //Spacing2 <- (' ' / "\t" / Comment)
            PrioritizedChoice pc1 = new PrioritizedChoice(
                    new CharClass(CharClass.matchOneOf(" \t")), new Comment());
            boolean match = pc1.acceptTrue();
            if (match) {
                m_text = super.toString();
            }
            return match;
        }

        private String m_text;

        @Override
        public String toString() {
            return m_text;
        }

        @Override
        public Acceptor create() {
            return new Spacing2();
        }
    }
}
