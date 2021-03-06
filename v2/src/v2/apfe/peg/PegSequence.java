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

import v2.apfe.peg.generate.GenJava;
import v2.apfe.runtime.Acceptor;
import v2.apfe.runtime.Marker;
import v2.apfe.runtime.Memoize;
import v2.apfe.runtime.Repetition;
import v2.apfe.runtime.Util;
import static v2.apfe.runtime.Util.toList;
import java.util.List;

public class PegSequence extends Acceptor implements GenJava.IGen {

    @Override
    public GenJava genJava(GenJava j) {
        //TODO: if empty?
        assert (null != getPrefixes() && (0 < getPrefixes().size()));
        if (1 == getPrefixes().size()) {
            j = j.append(getPrefixes().get(0));
        } else {
            j = j.funcCall("new Sequence", getPrefixes());
        }
        return j;
    }

    @Override
    protected boolean accepti() {
        // Sequence <- Prefix*
        Repetition r1 = new Repetition(new Prefix(), Repetition.ERepeat.eZeroOrMore);
        boolean match = (null != (r1 = match(r1)));
        if (match) {
            m_pfxs = toList(r1.getAccepted());
        }
        return match;
    }

    private List<Prefix>  m_pfxs;
    
    public List<Prefix> getPrefixes() {
        return m_pfxs;
    }
    
    @Override
    public String toString() {
        return Util.toString(m_pfxs).toString();
    }

    @Override
    public Acceptor create() {
        return new PegSequence();
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
     * Memoize for all instances of PegSequence.
     */
    private static Memoize stMemo = new Memoize();
}
