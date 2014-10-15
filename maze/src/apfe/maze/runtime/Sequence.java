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
 * An acceptor which succeeds only if the sequence of acceptors succeeds.
 * @author gburdell
 */
public class Sequence extends Acceptor {

    public Sequence(Acceptor... eles) {
        m_eles = eles;
    }

    private final Acceptor m_eles[];

    @Override
    public RatsNest accept(RatsNest rats) {
        RatsNest ins = rats.clone();
        RatsNest outs = null;
        for (Acceptor acc : m_eles) {
            outs = acc.accept(ins);
            if ((null == outs) || outs.isEmpty()) {
                break;
            }
            ins = outs;
        }
        return (null != outs) ? outs : new RatsNest();
    }

}
