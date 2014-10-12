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
 * Base class for a NonTerminal.
 *
 * @author gburdell
 */
public abstract class NonTerminal extends Acceptor {

    protected NonTerminal(Acceptor start) {
        m_start = start;
    }

    private final Acceptor m_start;

     @Override
    public RatsNest accept(RatsNest rats) {
        //dup, since were gonna unconditionally modify
        RatsNest outs = rats.clone();
        for (Rat rat : outs) {
            rat.addAccepted(new Ele(getClass(), Ele.EType.eEnter));
        }
        outs = m_start.accept(outs);
        for (Rat rat : outs) {
            rat.addAccepted(new Ele(getClass(), Ele.EType.eExit));
        }
        return outs;
    }

    
    public static class Ele implements Path.Ele {

        public static enum EType {

            eEnter, eExit
        };

        public Ele(Class nonTerm, EType type) {
            m_nonTerm = nonTerm;
            m_type = type;
        }

        @Override
        public String toString() {
            return m_nonTerm.getSimpleName()+ "-" + m_type.toString();
        }
        
        
        public final EType m_type;
        public final Class m_nonTerm;
    }

}
