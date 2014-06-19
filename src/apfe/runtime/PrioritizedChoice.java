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

/**
 * A Definition consisting of choices: alts[0] / alts[1] / ...
 *
 * @author gburdell
 */
public class PrioritizedChoice extends Acceptor {

    /**
     * Test if one of the prioritized choices was accepted.
     *
     * @return true if one choice was accepted.
     */
    public boolean hasAccepted() {
        return (null != m_accepted);
    }

    public int whichAccepted() {
        return hasAccepted() ? m_acceptedIx : -1;
    }

    /**
     * Get accepted choice (or null).
     *
     * @return accepted choice or null.
     */
    public Acceptor getAccepted() {
        return m_accepted;
    }
    private final Choices m_alts;
    private Acceptor m_accepted;
    private int m_acceptedIx;

    public PrioritizedChoice(Choices alts) {
        m_alts = alts;
    }

    /**
     * Constructor which takes already created set of Acceptor (choices).
     * Tradeoff here, compared to one which takes interface is that it is
     * assumed implemented interface just creates the Acceptor[i] when it
     * is needed (i.e., if choice[j] chosed where j is (much) less than
     * number of choices, then better runtime (save constructors of all choices).
     * So, this one might be useful for choices of Operators or such.
     * @param acc already created Acceptor choices.
     */
    public PrioritizedChoice(Acceptor... acc) {
        final Acceptor a[] = acc;
        m_alts = new Choices() {
            @Override
            public Acceptor getChoice(int ix) {
                return (ix < a.length) ? a[ix] : null;
            }
        };
    }

    @Override
    public String toString() {
        String s;
        if (hasAccepted()) {
            s = getAccepted().toString();
        } else {
            s = "";
        }
        return s;
    }

    @Override
    protected boolean accepti() {
        boolean match = false;
        Acceptor choice;
        State st = State.getTheOne();
        for (m_acceptedIx = 0; null != (choice = m_alts.getChoice(m_acceptedIx)); m_acceptedIx++) {
            match = (null != (choice = match(choice)));
            if (match) {
                m_accepted = choice;
                break;
            }
        }
        ParseError.reduce();
        /**
         * It is possible that no alternatives cleared ILR detection, as would
         * be case with complex/nested alternatives.
         */
        return match;
    }

    @Override
    public Acceptor create() {
        return new PrioritizedChoice(m_alts);
    }

    /**
     * An Acceptor with PrioritizedChoice implements the Choices interface for
     * efficiency. Otherwise, an Acceptor would construct all choices upon
     * creation. With this interface, the alternative Acceptor (choices) are
     * created on demand (callback).
     */
    public static interface Choices {

        public Acceptor getChoice(int ix);
    }
}
