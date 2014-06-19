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
package apfe.slf;

import apfe.runtime.Acceptor;
import apfe.runtime.CharClass;
import apfe.runtime.CharSeq;
import apfe.runtime.EndOfLine;
import apfe.runtime.ICharClass;
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
        // SL_COMMENT  <- "//" (!EOL .)* EOL
        // ML_COMMENT  <- "/*" (!"*/" .)* "*/"
        //
        PrioritizedChoice pc1 = new PrioritizedChoice(new PrioritizedChoice.Choices() {
            @Override
            public Acceptor getChoice(int ix) {
                Sequence s1 = null;
                switch(ix) {
                    case 0:
                    {
                        // SL_COMMENT  <- "//" (!EOL .)* EOL
                        NotPredicate np1 = new NotPredicate(new EndOfLine());
                        Repetition rp1 = new Repetition(
                                new Sequence(np1, new CharClass(ICharClass.IS_ANY)), Repetition.ERepeat.eZeroOrMore);
                        s1 = new Sequence(new CharSeq("//"), rp1, new EndOfLine());
                    }
                        break;
                    case 1:
                    {
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
        }) ;
        boolean match = (null != (pc1 = match(pc1)));
        if (match) {
            //TODO
        }
        return match;
    }
    
    private StringBuilder m_cmnt;

    @Override
    public String toString() {
        return (null == m_cmnt) ? null : m_cmnt.toString();
    }

    @Override
    public Acceptor create() {
        return new Comment();
    }

}
