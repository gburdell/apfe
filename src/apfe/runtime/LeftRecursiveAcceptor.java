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
package apfe.runtime;
import static gblib.Util.downCast;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Non-terminals with direct left recursion will extend this class.
 *
 * @author gburdell
 */
public abstract class LeftRecursiveAcceptor extends Acceptor {

    /**
     * Get index into Choices of 1st non-left-recursive alternative.
     *
     * @return 1st non-recursive index.
     */
    protected abstract int getNonRecursiveChoiceIx();

    /**
     * Implement for left-recursion.
     *
     * @param isPredicate
     * @return
     */
    @Override
    protected Acceptor accept(boolean isPredicate) {
        //TODO: dont handle predicates yet
        assert false == isPredicate;
        //TODO: is it really this simple?
        Acceptor acc = super.accept(isPredicate);
        return acc;
    }

    public boolean hasSeed() {
        return (0 < m_lrCnt);
    }

    protected abstract Acceptor getChoice(int ix);

    /**
     * All LeftRecursive subclass will just use this one.
     * But, may overload (augment) for Listeners.
     *
     * @return true if (subclass) accepts sequence.
     */
    @Override
    protected boolean accepti() {
        boolean ok = false;
        if (!hasSeed()) {
            final int startNonLrIx = getNonRecursiveChoiceIx();
            //iterate through non left-recursive the first time through
            Acceptor cand = null;
            for (int i = startNonLrIx; !ok && (null != (cand = getChoice(i))); i++) {
                cand = Acceptor.match(cand);
                ok = (null != cand);
                /**
                 * If ok, then cand is one of the subclass getChoice(). And the
                 * subclass has already tracked that.
                 */
            }
            while (ok) {
                /**
                 * Upon entry, we have matched one of the non-left-recursive.
                 *
                 * We have also captured the essence of the match already in the
                 * subclass, so really just need subclass to grow seed.
                 */
                growSeed();
                if (m_isDRR) {
                    //only 1 pass on definite right recursion
                    assert 1==m_lrCnt;
                    break;
                }
                /**
                 * Now we want to try the left recursive a <- a b ... / a c ...
                 */
                ok = false;
                //NOTE: dont need to refresh alts since we are same object. 
                //alts = getChoices();
                //Try all the alternatives with left recursion
                for (int i = 0; !ok && (i < startNonLrIx); i++) {
                    cand = getChoice(i);
                    cand = Acceptor.match(cand);
                    ok = (null != cand);
                }
            }
            //We may have just matched a non-recursive and nothing more
            ok = hasSeed();
        } else {
            //We entered this matching left recursive
            ok = true;
            //TODO: the idea is that this 1st match 
            //(e.g. just after 1st a, marked by _ of a <- a_ b ...)
            //has already pushed the seed on the stack.
            //really this simple?
        }
        return ok;
    }

    protected void growSeed() {
        if (null == m_items) {
            m_items = new LinkedList<>();
        }
        assert null != m_lastChoice;
        if (m_lastIx >= getNonRecursiveChoiceIx()) {
            m_items.add(m_lastChoice);
        } else {
            //We must have a sequence and drop the LeftRecursive part.
            Sequence asSeq = downCast(m_lastChoice);
            Acceptor accs[] = asSeq.getAccepted();
            Acceptor rem[] = Arrays.copyOfRange(accs, 1, accs.length);
            asSeq = new Sequence(rem);
            m_items.add(asSeq);
        }
        m_lrCnt++;
    }
    
    @Override
    public String toString() {
        String s = (null != m_items && !m_items.isEmpty()) ? m_items.get(0).toString() : "";
        return s;
    }

    protected LeftRecursiveAcceptor() {
        this(false);
    }
    
    protected LeftRecursiveAcceptor(boolean isDRR) {
        m_isDRR = isDRR;
    }
    
    public List<Acceptor> getItems() {
        return m_items;
    }
    
    /**
     * True if definite right recursion
     */
    private final boolean m_isDRR;

    protected LinkedList<Acceptor> m_items;

    protected int m_lrCnt = 0;

    
    /**
     * Track last acceptor choice, so if we accept seed, then we know which
     * choice was matched.
     */
    protected Acceptor m_lastChoice = null;
    /**
     * Track last choice index, so if we accept the seed, then we know which
     * choice was selected.
     */
    protected int m_lastIx = -1;
}
