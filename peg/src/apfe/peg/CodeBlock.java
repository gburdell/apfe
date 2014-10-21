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

import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer.Marker;
import apfe.runtime.CharClass;
import apfe.runtime.ICharClass;
import apfe.runtime.Memoize;
import apfe.runtime.NotPredicate;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;
import java.util.List;

public class CodeBlock extends Acceptor {

    public CodeBlock() {
    }

    private static final Operator stLeft = new Operator(Operator.EOp.LCURLY2),
            stRight = new Operator(Operator.EOp.RCURLY2);
    
    @Override
    protected boolean accepti() {
        //CodeBlock <- LCURLY2 (!RCURLY2 .)* RCURLY2
        NotPredicate np1 = new NotPredicate(stRight.create());
        CharClass cc1 = new CharClass(ICharClass.IS_ANY);
        Sequence s1 = new Sequence(np1, cc1);
        Repetition rep1 = new Repetition(s1, Repetition.ERepeat.eZeroOrMore);
        Sequence s2 = new Sequence(stLeft.create(), rep1, stRight.create());
        boolean match = (null != (s2 = match(s2)));
        if (match) {
            rep1 = Util.extractEle(s2, 1);
            List<CharClass> lcc = Util.extractList(rep1, 1);
            m_codeBlk = Util.toString(lcc);
        }
        return match;
    }
    
    private StringBuilder m_codeBlk;
    
    public String getCodeBlk() {
        return Util.toString(m_codeBlk);
    }
    
    @Override
    public String toString() {
        return Util.toString(stLeft).append('\n').append(m_codeBlk)
                .append('\n').append(stRight).append('\n').toString();
    }
    
    private Identifier  m_id;
    private Expression  m_expr;

    public Identifier getId() {
        return m_id;
    }

    public Expression getExpr() {
        return m_expr;
    }
    
    @Override
    public Acceptor create() {
        return new CodeBlock();
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
     * Memoize for all instances of Definition.
     */
    private static Memoize stMemo = new Memoize();
}
