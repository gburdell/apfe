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

import static apfe.maze.runtime.RunMaze.addNewRat;
import static apfe.maze.runtime.Util.isNull;

/**
 * @author gburdell
 */
public class Repetition implements Acceptor {
    public static enum EAlgo {
        eExhaustive
    }
    /**
     * Star closure (0 or more elements).
     * @param opt repeated element.
     */
    public Repetition(Acceptor opt) {
        this(opt, false, EAlgo.eExhaustive);
    }

    /**
     * 0/1 or more elements.
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
     * If ele*, then return true (unconditionally) and launch new rats
     * after each repetition which passes (so long as state changes).
     * If ele+, then return true if at least 1 accepted; then launch new rats...
     * @param visitor rat will be updated with the longest sequence which
     * was accepted.  New rats will be launched
     */
    @Override
    public boolean accept(Rat visitor) {
        //only implemented this one so far.
        //TODO: add some interface for algo so we cn plug-n-play.
        assert (EAlgo.eExhaustive == m_algorithm);
        boolean ok;
        State prevState = null;
        int acceptedCnt = 0;
        RatQueue newRats = new RatQueue();
        while (ok = m_opt.accept(visitor)) {
            if ((null != prevState) && prevState.equals(visitor.getState())) {
                break;  //state has not advanced
            }
            acceptedCnt++;
            prevState = visitor.getState();
            
            newRats.add(new Rat(visitor, m_opt));
        }
        return true;
    }
}
