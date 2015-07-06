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
package v2.apfe.peg;

import v2.apfe.peg.generate.Main;
import v2.apfe.runtime.Acceptor;
import v2.apfe.runtime.Marker;
import v2.apfe.runtime.Memoize;
import v2.apfe.runtime.PrioritizedChoice;
import v2.apfe.runtime.Repetition;
import v2.apfe.runtime.Sequence;
import v2.apfe.runtime.Util;
import static v2.apfe.runtime.Util.extractEle;
import java.util.HashMap;
import java.util.Map;

public class Definition extends Acceptor {

    public Definition() {
    }

    private static final Operator stLeftArrow = new Operator(Operator.EOp.LEFTARROW);
    private static final Operator stColon = new Operator(Operator.EOp.COLON);
    private static final Operator stExtOp = new Operator(Operator.EOp.EXT_OP);
    private static final Operator stSemi = new Operator(Operator.EOp.SEMI);
    private static final Operator stDot = new Operator(Operator.EOp.DOT);

    private boolean m_isLeftRecursive = false;

    public void setIsLeftRecursive(boolean isLeftRecursive) {
        m_isLeftRecursive = isLeftRecursive;
    }

    public boolean getIsLeftRecursive() {
        return m_isLeftRecursive;
    }

    @Override
    protected boolean accepti() {
        // Definition <- Identifier '.'
        //             / Identifier (LEFTARROW / COLON) Expression CodeBlock? SEMI?
        //             / Identifier "<<" Identifier
        Repetition r1 = new Repetition(new CodeBlock(), Repetition.ERepeat.eOptional);
        Repetition r2 = new Repetition(stSemi.create(), Repetition.ERepeat.eOptional);
        Sequence s1 = new Sequence(new Identifier(), stDot.create());
        PrioritizedChoice a1 = new PrioritizedChoice(stLeftArrow.create(), stColon.create());
        Sequence s2 = new Sequence(new Identifier(), a1, new Expression(), r1, r2);
        Sequence s3 = new Sequence(new Identifier(), stExtOp.create(), new Identifier());
        PrioritizedChoice p1 = new PrioritizedChoice(s1, s2, s3);
        boolean match;
        match = (null != (p1 = match(p1)));
        if (match) {
            s1 = gblib.Util.downCast(p1.getAccepted());
            switch (p1.whichAccepted()) {
                case 0:
                    m_id = extractEle(s1, 0);
                    m_isToken = true;
                    break;
                case 1:
                    m_id = extractEle(s1, 0);
                    m_expr = extractEle(s1, 2);
                    check();
                    r1 = extractEle(s1, 3);
                    if (0 != r1.sizeofAccepted()) {
                        assert false; //no codeblocks supported in generator
                        assert (1 == r1.sizeofAccepted());
                        m_codeBlk = gblib.Util.downCast(r1.getAccepted().get(0));
                    }
                    break;
                case 2:
                    m_id = extractEle(s1, 0);
                    m_extCls = extractEle(s1, 2);
                    assert !hasExtCls(m_id.getId());
                    stExtClsByDefn.put(m_id.getId(), m_extCls);
                    break;
                default:
                    assert false;
            }
        }
        return match;
    }

    private void check() {
        //Look for any empty alts
        int n = 1;
        for (PegSequence ps : getExpr().getSequences()) {
            if ((null == ps) || (null == ps.getPrefixes())
                    || (1 > ps.getPrefixes().size())) {
                gblib.Util.error("EMPTY-1", m_id.getId(), n);
            }
            n++;
        }
    }

    @Override
    public String toString() {
        if (isToken()) {
            return m_id.toString() + ".\n";
        } else if (isExtCls()) {
            return Util.toString(m_id, stExtOp, m_extCls).append("\n").toString();
        } else {
            return Util.toString(m_id, stDelim, m_expr, m_codeBlk).append(" ;\n").toString();
        }
    }
    
    public static final Acceptor stDelim = Main.stGenMaze ? stColon : stLeftArrow;

    public boolean isToken() {
        return m_isToken;
    }

    private Identifier m_id, m_extCls;
    private Expression m_expr;
    private CodeBlock m_codeBlk;
    private boolean m_isToken = false;

    public Identifier getId() {
        return m_id;
    }

    public Expression getExpr() {
        return m_expr;
    }

    public Identifier getExtCls() {
        assert isExtCls();
        return m_extCls;
    }

    public static Identifier getExtCls(String id) {
        return stExtClsByDefn.get(id);
    }

    public static boolean hasExtCls(String id) {
        return stExtClsByDefn.containsKey(id);
    }

    public boolean isExtCls() {
        return (null != m_extCls);
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

    /**
     * Map of Definition name to its class name. Used only if external.
     */
    private static Map<String, Identifier> stExtClsByDefn = new HashMap<>();
}
