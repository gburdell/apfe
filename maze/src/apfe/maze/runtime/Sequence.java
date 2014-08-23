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

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * A sequence of one or more AcceptorBase.
 *
 * @author gburdell
 */
public class Sequence extends AcceptorBase {

    /**
     * Acceptor with sequence semantics.
     *
     * @param eles sequence of Acceptor prescribed.
     */
    public Sequence(AcceptorBase... eles) {
        m_eles = Util.arrayAsQueue(eles);
    }

    private Sequence(Queue<AcceptorBase> eles) {
        m_eles = new ArrayDeque<>(eles.size());
        for (AcceptorBase ele : eles) {
            m_eles.add(ele.create());
        }
    }
    
    @Override
    public AcceptorBase create() {
        //deep clone
        return new Sequence(m_eles);
    }

    @Override
    protected boolean acceptImpl() {
        AcceptorBase top = m_eles.remove();
        boolean match = top.acceptImpl();
        while (match) {
            for (AcceptorBase leaf : top.getAccepted()) {
                /*
                TODO: No. Just add remaining m_eles and w/ asParent start state
                to work on.
                We cant assess match until all successors are done.
                */
            }
        };
        return match;
    }

    private final Queue<AcceptorBase> m_eles;
}
