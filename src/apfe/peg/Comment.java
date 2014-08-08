/*
 * The MIT License
 *
 * Copyright 2013 gburdell.
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
package apfe.peg;

import apfe.runtime.EndOfLine;
import apfe.runtime.Acceptor;
import static apfe.runtime.Acceptor.match;
import apfe.runtime.CharBuffer.Marker;
import apfe.runtime.CharClass;
import apfe.runtime.CharSeq;
import apfe.runtime.ICharClass;
import apfe.runtime.Memoize;
import apfe.runtime.NotPredicate;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;

public class Comment extends Acceptor {

    public Comment() {
    }

    @Override
    protected boolean accepti() {
        // Comment <- SL_COMMENT / ML_COMMENT
        // SL_COMMENT  <- ('#' / "//") (!EOL .)* EOL
        // ML_COMMENT  <- "/*" (!"*/" .)* "*/"
        //
        PrioritizedChoice pc1 = new PrioritizedChoice(new PrioritizedChoice.Choices() {
            @Override
            public Acceptor getChoice(int ix) {
                Sequence s1 = null;
                switch (ix) {
                    case 0: {
                        // SL_COMMENT  <- ('#' / "//") (!EOL .)* EOL
                        NotPredicate np1 = new NotPredicate(new EndOfLine());
                        Repetition rp1 = new Repetition(
                                new Sequence(np1, new CharClass(ICharClass.IS_ANY)), Repetition.ERepeat.eZeroOrMore);
                        PrioritizedChoice pc1 = new PrioritizedChoice(
                                new Char('#'), new CharSeq("//"));
                        s1 = new Sequence(pc1, rp1, new EndOfLine());
                    }
                    break;
                    case 1: {
                        // ML_COMMENT  <- "/*" (!"*/" .)* "*/"
                        NotPredicate np1 = new NotPredicate(new CharSeq("*/"));
                        Repetition rp1 = new Repetition(
                                new Sequence(np1, new CharClass(ICharClass.IS_ANY)), Repetition.ERepeat.eZeroOrMore);
                        s1 = new Sequence(new CharSeq("/*"), rp1, new CharSeq("*/"));
                    }
                    break;
                }
                return s1;
            }
        });
        boolean match = (null != (pc1 = match(pc1)));
        if (match) {
            m_cmnt = pc1.toString();
        }
        return match;
    }

    /**
    @Override
    protected boolean XXaccepti() {
        // Comment <- '#' (!EndOfLine .)* EndOfLine
        CharSeq cs1 = new CharSeq('#');
        NotPredicate np1 = new NotPredicate(new EndOfLine());
        CharClass cc1 = new CharClass(ICharClass.IS_ANY);
        Repetition r1 = new Repetition(new Sequence(np1, cc1), Repetition.ERepeat.eOneOrMore);
        Sequence s1 = new Sequence(cs1, r1, new EndOfLine());
        boolean match = (null != (s1 = match(s1)));
        if (match) {
            m_cmnt = new StringBuilder();
            cs1 = Util.extractEle(s1, 0);
            m_cmnt.append(cs1);
            r1 = Util.extractEle(s1, 1);
            List<CharClass> lcc = Util.extractList(r1, 1);
            Util.toString(m_cmnt, lcc).append("\n");
        }
        return match;
    }
**/
    
    private String m_cmnt;

    @Override
    public String toString() {
        return m_cmnt;
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
}
