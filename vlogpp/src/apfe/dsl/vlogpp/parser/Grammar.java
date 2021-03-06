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
package apfe.dsl.vlogpp.parser;

import apfe.runtime.Acceptor;
import apfe.runtime.Marker;
import apfe.runtime.EndOfFile;
import apfe.runtime.InputStream;
import apfe.runtime.Memoize;
import apfe.runtime.ParseError;
import apfe.runtime.Sequence;
import apfe.runtime.CharBufState;
import apfe.runtime.CharBuffer;
import apfe.runtime.Util;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gburdell
 */
public class Grammar extends Acceptor {

    @Override
    protected boolean accepti() {
        Sequence s1 = new Sequence(new Contents(), new EndOfFile());
        boolean match = (null != (s1 = match(s1)));
        if (match) {
            m_contents = Util.extractEle(s1, 0);
        }
        return match;
    }

    private Contents m_contents;

    @Override
    public Acceptor create() {
        return new Grammar();
    }

    @Override
    protected void memoize(Marker mark, Marker endMark) {
        stMemo.add(mark, this, endMark);
    }

    @Override
    protected Memoize.Data hasMemoized(Marker mark) {
        return stMemo.memoized(mark);
    }

    /**
     * Memoize for all instances of Grammar.
     */
    private static Memoize stMemo = new Memoize();
    
    public static void main(String argv[]) {
        try {
            final String fn = argv[0];
            System.out.println("accepti");
            InputStream fins = new InputStream(fn);
            CharBuffer cbuf = fins.newCharBuffer();
            CharBufState st = CharBufState.create(cbuf);
            Grammar gram = new Grammar();
            Acceptor acc = gram.accept();
            if (null != acc) {
                String ss = acc.toString();
                System.out.println("returns:\n========\n"+ss);
            }
            boolean result = (null != acc) && st.isEOF();
            if (!result) {
                ParseError.printTopMessage();
            }
            assert(result);
        } catch (Exception ex) {
            gblib.Util.abnormalExit(ex);
        }
    }
}
