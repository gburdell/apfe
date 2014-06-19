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
package apfe.leftrecursive;

import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer;
import apfe.runtime.State;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gburdell
 */
public class TermTest {

    private static final String stBuf[] = {
        "1?2:3",
        "1*2/3",
        "4",
        "5*6/7*8/9",
        "10+11/12*13+14",
        "15-16*17/18-19+20*21",
        "1+2?3:4",
        "1+2?3+4:5/6-7*8",
        "1+2?3/4-5?6-7*8:9-10:12-13"
    };

    /**
     * Test of accepti method, of class Term.
     */
    @Test
    public void testAccepti() {
        System.out.println("accepti");
        for (String tt : stBuf) {
            test(tt);
        }
    }

    public static void test(final String tt) {
        System.out.print("term: " + tt + ": parses as: ");
        final char cbuf[] = tt.toCharArray();
        CharBuffer buf = new CharBuffer("<test>", cbuf, cbuf.length);
        State st = State.create(buf);
        boolean result;
        Term gram = new Term();
        Acceptor acc = gram.accept();
        if (null != acc) {
            String ss = acc.toString();
            System.out.println(ss);
        }
        result = (null != acc) && st.isEOF();
        assertTrue(result);
    }
}
