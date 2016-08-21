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
import static gblib.Util.downCast;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A Definition consisting of repeated element: e?, e* or e+
 *
 * @author gburdell
 */
public class Repetition extends Acceptor {

    public static enum ERepeat {

        eOptional, eZeroOrMore, eOneOrMore
    };

    /**
     * Get accepted sequence.
     *
     * @return accepted sequence.
     */
    public List<Acceptor> getAccepted() {
        return (null != m_accepted) ? m_accepted : Collections.<Acceptor>emptyList();
    }

    /**
     * Get 1st/only accepted element.
     * @param <T> type of accepted.
     * @return 1st/only accepted item.
     */
    public <T extends Acceptor> T getOnlyAccepted() {
        assert 1==sizeofAccepted();
        return downCast(getAccepted().get(0));
    }
    
    public int sizeofAccepted() {
        return getAccepted().size();
    }
    private final ERepeat m_rep;
    private final Acceptor m_defn;
    private List<Acceptor> m_accepted;

    public Repetition(Acceptor defn, ERepeat rep) {
        m_defn = defn;
        m_rep = rep;
    }

    private void add(Acceptor defn) {
        if (null == m_accepted) {
            m_accepted = new LinkedList<>();
        }
        m_accepted.add(defn);
    }

    @Override
    protected boolean accepti() {
        boolean match = true;
        Acceptor defn = m_defn;
        while (match) {
            match = (null != (defn = match(defn)));
            if (match) {
                add(defn);
                if (ERepeat.eOptional == m_rep) {
                    break;  //while: we're done
                }
                defn = m_defn.create();
            }
        }
        switch (m_rep) {
            case eOptional:
            case eZeroOrMore:
                match = true;
                break;
            case eOneOrMore:
                match = (0 < sizeofAccepted());
                break;
            default:
                assert false;
        }
        return match;
    }

    @Override
    public Acceptor create() {
        return new Repetition(m_defn.create(), m_rep);
    }

    /**
     * Return String representation. Creates on 1st invocation, since in some
     * cases, the String representation makes no sense and will never be called
     * for.
     *
     * @return String representation (or null).
     */
    @Override
    public String toString() {
        if (1 > sizeofAccepted()) {
            return null;
        }
        if (null == m_str) {
            m_str = new StringBuilder();
            for (Acceptor d : getAccepted()) {
                m_str.append(d);
            }
        }
        return m_str.toString();
    }
    private StringBuilder m_str;
    
}
