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
     * @param rats collection of rats.
     * @return updated set of rats which accepted this terminal.
     */
    @Override
    public RatsNest accept(RatsNest rats) {
        RatsNest rvals = new RatsNest();
        for (Rat rat : rats) {
            if (getTokCode() == rat.peekCode()) {
                rat = rat.clone();
                rat.addAccepted(new Ele(rat.advance()));
                rvals.add(rat);
            }
        }
        return rvals;
    }
    
    

    public static class Ele implements Path.Ele {
        public Ele(Token tok) {
            m_accepted = tok;
        }

        @Override
        public String toString() {
            return m_accepted.getText();
        }
        
        private final Token m_accepted;
    }
}
