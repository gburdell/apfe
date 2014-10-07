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
 * Base class for a Terminal.
 * There is no state associated with this object so static view of maze can
 * be built.
 *
 * @author gburdell
 */
public abstract class Terminal extends Acceptor {

    protected abstract int getTokCode();

    /**
     * Test if rat's next token is terminal which matches the one from subclass
     * getTokCode(). If so, we add this to payload.
     *
     * @param visitor rat visitor.
     * @return true if visitor;s next token matches this one; else false.
     */
    @Override
    public boolean accept(Rat visitor) {
        if (getTokCode() != visitor.peekCode()) {
            return false;
        }
        visitor.addAccepted(new Ele(visitor.advance()));
        return true;
    }

    public static class Ele implements Path.Ele {
        public Ele(Token tok) {
            m_accepted = tok;
        }
        private final Token m_accepted;
    }
}
