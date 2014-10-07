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

import static apfe.maze.runtime.RunMaze.addNewRat;
import static apfe.maze.runtime.Util.isNull;

/**
 * @author gburdell
 */
public class Repetition implements Acceptor {

    /**
     * Star closure (0 or more elements).
     * @param opt repeated element.
     */
    public Repetition(Acceptor opt) {
        this(opt, false);
    }

    /**
     * 0/1 or more elements.
     * @param opt repeated element.
     * @param oneOrMore true for one or more repeated elements (else 0 or more).
     */
    public Repetition(Acceptor opt, boolean oneOrMore) {
        m_opt = opt;
        m_isOneOrMore = oneOrMore;
    }

    private final Acceptor m_opt;
    private final boolean m_isOneOrMore;

    /**
     * If ele*, then return true (unconditionally) and launch new rats
     * after each repetition which passes (so long as state changes).
     * If ele+, then return true if at least 1 accepted; then launch new rats...
     */
    @Override
    public boolean accept(Rat visitor) {
        return true;
    }
}
