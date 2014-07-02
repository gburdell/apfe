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
package apfe.runtime;

import apfe.runtime.CharBuffer.Marker;

/**
 * Base class for all nonterminals, sequences, predicates.
 *
 * @author gburdell
 */
public abstract class Acceptor {

    /**
     * Test if subclass can accept.
     *
     * @param <T> subclass.
     * @param a instance of (subclass) Acceptor to test.
     * @return instance of subclass, if accepted; else null.
     */
    public static <T extends Acceptor> T match(Acceptor a) {
        return Util.downCast(a.accept());
    }

    /**
     * Test if subclass can accept.
     *
     * @param <T> subclass.
     * @param a instance of (subclass) Acceptor to test.
     * @return true if accepted, else false.
     */
    public static <T extends Acceptor> boolean matchTrue(Acceptor a) {
        return (null != match(a));
    }

    /**
     * A deep clone.
     *
     * @param eles elements to clone.
     * @return deep clone.
     */
    public static Acceptor[] create(final Acceptor eles[]) {
        final int n = eles.length;
        assert 0 < n;
        Acceptor dup[] = new Acceptor[n];
        for (int i = 0; i < n; i++) {
            dup[i] = eles[i].create();
        }
        return dup;
    }

    public abstract Acceptor create();

    /**
     * Return true if (implementation) accepts from current State.
     *
     * @return true if implementation accepts from current States.
     */
    protected abstract boolean accepti();

    private CharBuffer.Marker m_startMark;
    
    protected CharBuffer.Marker getStartMark() {
        return m_startMark;
    }
    
    /**
     * Determine if implementation accepts from current State. If not, rewind
     * token buffer.
     *
     * @param isPredicate true if check is made, then unconditional rewind.
     * @return Acceptor (subclass instance) if implementation accepts token
     * sequence.
     */
    protected Acceptor accept(boolean isPredicate) {
        State st = State.getTheOne();
        CharBuffer.Marker mark = st.getBuf().mark();
        m_startMark = mark;
        Acceptor accepted = this;
        boolean ok = false;

        //{track nonterm for debugging
        String nonTermId = getNonTermID();
        if (null != nonTermId) {
            st.pushNonTermID(nonTermId);
        }
        //}

        if (isPredicate) {
            st.incrPredicate();
        }
        /**
         * While processing (nested) predicates we dont any memoize. TODO: is
         * this optimal? Seems like for "true", we should memoize?
         */
        if (!st.inPredicate()) {
            Memoize.Data md = null;
            if (Memoize.stEnableMemoize) {
                md = hasMemoized(mark);
                if (null != md) {
                    accepted = md.getAcceptor();
                    mark = md.getMark();
                    st.getBuf().reset(mark);
                    ok = true;
                }
                st.updateMemoizeHit(ok);
            }
        }
        if (!ok) {
            ok = accepti();
            if (ok && !st.inPredicate() && Memoize.stEnableMemoize) {
                //memoize
                memoize(mark, st.getBuf().mark());
            }
        }
        if (!ok || isPredicate) {
            st.getBuf().reset(mark);
        }
        if (isPredicate) {
            st.decrPredicate();
        }
        if (ok) {
            String dbgText = toString();
            dbgText += "";
        }

        //we'll still track track since helps see whats going on
        if (null != nonTermId) {
            String x = st.popNonTermID();
            assert x.equals(nonTermId);
        }

        return ok ? accepted : null;
    }

    public boolean inPredicate() {
        return State.getTheOne().inPredicate();
    }

    @Override
    public String toString() {
        return State.getTheOne().getBuf().substring(m_startMark);
    }

    /**
     * Test if accepted. Predicates should override.
     *
     * @return Acceptor (subclass instance) if implementation accepts from
     * current State.
     */
    public Acceptor accept() {
        return accept(false);
    }

    /**
     * Test if accepted.
     * 
     * @return true if implementation accepts from current State. 
     */
    public boolean acceptTrue() {
        return (null != accept());
    }

    /**
     * A NonTerminal will implement this method to memoize the accepted data.
     *
     * @param mark start mark.
     * @param endMark end mark.
     */
    protected void memoize(Marker mark, Marker endMark) {
        //do nothing
    }

    /**
     * A NonTerminal will implement this method to return memoized data for this
     * start/mark, if available.
     *
     * @param mark start position.
     * @return memoized Data or null (if not exist).
     */
    protected Memoize.Data hasMemoized(Marker mark) {
        return null;
    }

    //TODO: use int after debugging.
    /**
     * Each subclass has a unique id.
     *
     * @return id for subclass.
     */
    /*
     public abstract int getNonTermID();    
     static protected int getNextID() {
     return stNextId++;
     }    
     private static int stNextId = 1;
     */
    /**
     * Get subclass ID. NOTE: While we are debugging, we will use the classname.
     * Only non-terminal subclass will override.
     *
     * @return subclass ID.
     */
    public String getNonTermID() {
        return null;
    }
}
