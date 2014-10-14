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
        this(opt, false);
    }

    public Repetition(Acceptor opt, boolean oneOrMore) {
        this(opt, oneOrMore, EAlgo.eExhaustive);
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
     * @param rats 
     * @return all possible paths
     */
    @Override
    public RatsNest accept(RatsNest rats) {
        RatsNest rval = new RatsNest();
        //only implemented this one so far.
        //TODO: add some interface for algo so we cn plug-n-play.
        assert (EAlgo.eExhaustive == m_algorithm);
        for (Rat rat : rats) {
            rval = accept(rval, rat);
        }
        return rval;
    }

    /**
     * Process one rat at a time.
     *
     * @param rvals cumulative return vals.
     * @param rat1 the one rat to process.
     * @return inputs rvals with possibly more rats.
     */
    private RatsNest accept(RatsNest rvals, Rat rat1) {
        if (!m_isOneOrMore) {
            rvals.add(rat1.clone()); //passthru
        }
        int acceptedCnt = 0;
        RatsNest ins, outs = new RatsNest(rat1);
        while (! outs.isEmpty()) {
            ins = outs.clone();
            outs = new RatsNest();
            for (Rat rat : ins) {
                RatsNest iter = filter(rat.getState(), m_opt.getAcceptor().accept(rat));
                if (!iter.isEmpty()) {
                    acceptedCnt++;
                    outs.addAll(iter);
                    rvals.addAll(iter.clone());
                }
            }
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
