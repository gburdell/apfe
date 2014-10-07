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
 * Base class of Terminals and Nonterminals alike.
 *
 * @author gburdell
 */
public abstract class Acceptor {

    /**
     * If subclass can accept the rat visitor, then it will also load up its
     * path with the series of acceptors along the way.
     *
     * @param visitor rat visitor.
     * @return true if visitor is accepted (and its payload updated).
     */
    public boolean accept(Rat visitor) {
        throw new UnsupportedOperationException("Should never happen.");
    }

    /**
     * Default implementation for processing multiple rats. This is suitable for
     * 1-to-1 (i.e., 1 in, 1 out) processing.
     * <B>NOTE: Rats which are not accepted are destroyed here.</B>
     *
     * @param rats set of rats to process.
     * @return new set of (updated) rats.
     */
    public RatsNest accept(RatsNest rats) {
        RatsNest rvals = new RatsNest();
        for (Rat rat : rats) {
            if (accept(rat)) {
                rvals.add(rat.clone()); //new generation
            } else {
                rat.destroy();
            }
        }
        return rvals;
    }
}
