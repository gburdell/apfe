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
package apfe.dsl.vlogpp;

import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer;
import apfe.runtime.CharClass;
import apfe.runtime.EndOfFile;
import apfe.runtime.Memoize;
import apfe.runtime.NotPredicate;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Sequence;
import apfe.runtime.Util;

/**
 *
 * @author gburdell
 */
public class NotChar4 extends Acceptor {

    @Override
    protected boolean accepti() {
        //NotChar4 <- !(',' / ')' / '}' / ']' / EOF)
        PrioritizedChoice pc1 = new PrioritizedChoice(
                new CharClass(CharClass.matchOneOf(",)}]")), new EndOfFile());
        NotPredicate np1 = new NotPredicate(pc1);
        boolean match = (null != (np1 = match(np1)));
        return match;
    }

    private Contents m_contents;

    @Override
    public Acceptor create() {
        return new NotChar4();
    }

}
