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
package v2.apfe.runtime;


/**
 * Accepts Token.
 * @author gburdell
 */
public class Terminal extends Acceptor {
    public Terminal(int tokCode) {
        m_expect = tokCode;
    }

    @Override
    public Acceptor create() {
        return new Terminal(m_expect);
    }

    @Override
    public String toString() {
        return m_accepted.getText();
    }
    
    public Token getAccepted() {
        return m_accepted;
    }
    
    private final int   m_expect;
    private Token       m_accepted;
    
    @Override
    protected boolean accepti() {
        final ScannerState st = ScannerState.asMe(); 
        final Token la = st.la();
        boolean match = la.getCode() == m_expect;
        if (match) {
            m_accepted = la;
            st.accept();
        } else {
            ParseError.push(la.getText(), "'"+st.getExpectedAsString(m_expect)+"'");
        }
        return match;
    }
    
}
