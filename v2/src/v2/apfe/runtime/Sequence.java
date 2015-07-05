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
package v2.apfe.runtime;

/**
 * A Definition consisting of series of elements: d1 d2 ...
 *
 * @author gburdell
 */
public class Sequence extends Acceptor {

    /**
     * Get accepted sequence.
     *
     * @return accepted sequence.
     */
    public Acceptor[] getAccepted() {
        return m_eles;
    }
    
    private Acceptor m_eles[];
    
    public Sequence(Acceptor ... eles) {
        m_eles = eles;
    }
    
    @Override
    protected boolean accepti() {
        boolean match = true;
        Acceptor accepted[] = new Acceptor[m_eles.length];
        int i = 0;
        for (Acceptor d : m_eles) {
            match &= (null != (d = match(d)));
            if (match) {
                accepted[i++] = d;
            } else {
                //Dont need to do anything special on detectedILR()
                break;
            }
        }
        if (match) {
            m_eles = accepted;
        }
        ParseError.reduce();
        return match;
    }
    
    @Override
    public Acceptor create() {
        return new Sequence(Acceptor.create(m_eles));
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Acceptor a : getAccepted()) {
            s.append(a.toString());
        }
        return s.toString();
    }
}
