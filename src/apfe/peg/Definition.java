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
import apfe.runtime.Memoize;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;
import static apfe.runtime.Util.extractEle;

public class Definition extends Acceptor {

    public Definition() {
    }

    private static final Operator stLeftArrow = new Operator(Operator.EOp.LEFTARROW);
    
    private boolean m_isLeftRecursive = false;

    public void setIsLeftRecursive(boolean isLeftRecursive) {
        m_isLeftRecursive = isLeftRecursive;
    }
    
    public boolean getIsLeftRecursive() {
        return m_isLeftRecursive;
    }
    
    @Override
    protected boolean accepti() {
        //Definition <- Identifier LEFTARROW Expression CodeBlock?
        Repetition r1 = new Repetition(new CodeBlock(), Repetition.ERepeat.eOptional);
        Sequence s1 = new Sequence(new Identifier(), stLeftArrow.create(), new Expression(), r1);
        boolean match = (null != (s1 = match(s1)));
        if (match) {
            m_id = extractEle(s1, 0);
            m_expr = extractEle(s1, 2);
            r1 = extractEle(s1, 3);
            if (0 != r1.sizeofAccepted()) {
                assert (1 == r1.sizeofAccepted());
                m_codeBlk = Util.downCast(r1.getAccepted().get(0));
            }
        }
        return match;
    }
    
    @Override
    public String toString() {
        return Util.toString(m_id, stLeftArrow, m_expr, m_codeBlk).append("\n").toString();
    }
    
    private Identifier  m_id;
    private Expression  m_expr;
    private CodeBlock   m_codeBlk;

    public Identifier getId() {
        return m_id;
    }

    public Expression getExpr() {
        return m_expr;
    }
    
    @Override
    public Acceptor create() {
        return new Definition();
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
