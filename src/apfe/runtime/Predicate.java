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
 * Check for sequence match and unconditionally backtrack.
 *
 * @author gburdell
 */
public abstract class Predicate extends Acceptor {

    protected final Acceptor m_seq;
    private final boolean m_isAnd; //else Not

    protected Predicate(Acceptor seq, boolean isAnd) {
        m_seq = seq;
        m_isAnd = isAnd;
    }

    @Override
    protected boolean accepti() {
        boolean match = m_seq.acceptTrue();
        match = m_isAnd ? match : !match;
        if (!match) {
            ParseError.reduce();
        }
        return match;
    }

    @Override
    public Acceptor accept() {
        return super.accept(true);
    }

    @Override
    public String toString() {
        return "";
    }
        
}
