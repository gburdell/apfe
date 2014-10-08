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
package apfe.maze.runtime;

/**
 * @author gburdell
 */
public class Repetition extends Acceptor {

    public static enum EAlgo {

        eExhaustive
    }

    /**
     * Star closure (0 or more elements).
     *
     * @param opt repeated element.
     */
    public Repetition(Acceptor opt) {
        this(opt, false, EAlgo.eExhaustive);
    }

    /**
     * 0/1 or more elements.
     *
     * @param opt repeated element.
     * @param oneOrMore true for one or more repeated elements (else 0 or more).
     * @param algorithm tune acceptor algorithm.
     */
    public Repetition(Acceptor opt, boolean oneOrMore, EAlgo algorithm) {
        m_opt = opt;
        m_isOneOrMore = oneOrMore;
        m_algorithm = algorithm;
    }

    private final Acceptor m_opt;
    private final boolean m_isOneOrMore;
    private final EAlgo m_algorithm;

    /**
     * If ele*, then return true (unconditionally) and launch new rats after
     * each repetition which passes (so long as state changes). If ele+, then
     * return true if at least 1 accepted; then launch new rats...
     *
     * @param rats rat will be updated with the longest sequence which was
     * accepted. New rats will be launched
     */
    @Override
    public RatsNest accept(RatsNest rats) {
        RatsNest rval = new RatsNest();
        //only implemented this one so far.
        //TODO: add some interface for algo so we cn plug-n-play.
        assert (EAlgo.eExhaustive == m_algorithm);
        boolean ok;
        State prevState = null;
        int acceptedCnt = 0;
        RatsNest newRats = new RatsNest();
        while (ok = m_opt.accept(visitor)) {
            if ((null != prevState) && prevState.equals(visitor.getState())) {
                break;  //state has not advanced
            }
            acceptedCnt++;
            prevState = visitor.getState();

            newRats.add(new Rat(visitor, m_opt));
        }
        return rval;
    }

    /**
     * Process one rat at a time.
     *
     * @param rvals cumulative return vals.
     * @param rat the one rat to process.
     * @return inputs rvals with possibly more rats.
     */
    private RatsNest accept(RatsNest rvals, Rat rat) {
        if (!m_isOneOrMore) {
            rvals.add(rat.clone()); //passthru
        }
        int acceptedCnt = 0;
        while (true) {
            RatsNest iter = filter(rat.getState(), m_opt.accept(rat));
            if (iter.isEmpty()) {
                break;
            }
            acceptedCnt++;
            rvals.addAll(iter);
            //TODO: process iter.
            //Not recursive: since cnt will not be updated.
            //dont add cnt to object state, since we will be static instance
            //use iter = iter.clone() and process inside while(true)...
        }
        return rvals;
    }

    /**
     * Only keep rats with state != start.
     *
     * @param start start state.
     * @param cands next generation.
     * @return only cands with updated state.
     */
    private static RatsNest filter(State start, RatsNest cands) {
        RatsNest rvals = new RatsNest();
        for (Rat rat : cands) {
            if (!start.equals(rat.getState())) {
                rvals.add(rat);
            }
        }
        return rvals;
    }
}
