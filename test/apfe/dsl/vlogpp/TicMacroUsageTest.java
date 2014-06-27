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

import apfe.dsl.vlogpp.parser.TicMacroUsage;
import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer;
import apfe.runtime.ParseError;
import apfe.runtime.State;
import apfe.runtime.Util;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gburdell
 */
public class TicMacroUsageTest {

    private static final String stBuf[] = {
        "`m1", "`m2(a )", "`m3(a,b,c,d)"
    };

    /**
     * Test of accepti method, of class Spacing.
     */
    @Test
    public void testAccepti() {
        System.out.println("accepti");
        for (String tt : stBuf) {
            test(tt);
        }
        {
            //dump memoize stats
            long stats[] = State.getTheOne().getMemoizeStats();
            double pcnt = 0;
            if (0 < stats[1]) {
                pcnt = (100.0 * stats[0]) / stats[1];
            }
            Util.info("STAT-1", stats[0], stats[1], pcnt);
        }
    }

    public static void test(final String tt) {
        System.out.print("==============================\nTicMacroUsage: " + tt + ": parses as: ");
        final char cbuf[] = tt.toCharArray();
        CharBuffer buf = new CharBuffer("<test>", cbuf, cbuf.length);
        State st = State.create(buf);
        boolean result;
        TicMacroUsage gram = new TicMacroUsage();
        Acceptor acc = gram.accept();
        if (null != acc) {
            String ss = acc.toString();
            System.out.println(ss);
        }
        result = (null != acc) && st.isEOF();
        if (!result) {
            ParseError.printTopMessage();
        }
        assertTrue(result);
    }
}
