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

    public Acceptor itemAt(int ix) {
        return getAccepted()[ix];
    }

    public String getText(int ix) {
        return m_texts[ix];
    }

    public String getTexts(int begin, int end) {
        return Util.extractEleAsString(this, begin, end);
    }
    
    public int length() {
        return m_eles.length;
    }

    private Acceptor m_eles[];
    private String m_texts[];

    public Sequence(Acceptor... eles) {
        m_eles = eles;
    }

    @Override
    protected boolean accepti() {
        boolean match = true;
        Acceptor accepted[] = new Acceptor[m_eles.length];
        String texts[] = new String[m_eles.length];
        final CharBufState cbuf = CharBufState.asMe();
        Marker start;
        int i = 0;
        for (Acceptor d : m_eles) {
            start = cbuf.getCurrentMark();
            match &= (null != (d = match(d)));
            if (match) {
                accepted[i] = d;
                texts[i] = cbuf.substring(d.getStartMark());
                i++;
            } else {
                //Dont need to do anything special on detectedILR()
                break;
            }
        }
        if (match) {
            m_eles = accepted;
            m_texts = texts;
        } else {
            m_eles = new Acceptor[0];
            m_texts = new String[0];
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
        for (String i : m_texts) {
            s.append(i);
        }
        return s.toString();
    }
}
