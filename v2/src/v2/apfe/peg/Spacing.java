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
package v2.apfe.peg;

import apfe.runtime.Space;
import apfe.runtime.Acceptor;
import apfe.runtime.BaseSpacing;

/**
 * @author gburdell
 */
public class Spacing extends BaseSpacing {
    /**
     * Useful method for testing for Spacing sequence, for cases when you do
     * not want the actual sequence.
     * @return true if match Spacing.
     */
    public static boolean matchTrue() {
        return matchTrue(new Spacing());
    }

    public Spacing() {
        super(new Space(), new Comment());
    }

    @Override
    public Acceptor create() {
        return new Spacing();
    }
}
