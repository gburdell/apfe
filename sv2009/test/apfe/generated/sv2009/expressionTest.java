package apfe.generated.sv2009;

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



import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer;
import apfe.runtime.State;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gburdell
 */
public class expressionTest {
     private static final String stBuf[] = {
         "z[2]",
         "a", 
         "b | c", 
         "~d",
         "~(e ^ f)",
         "~(g[1] & g[2])"
     };
         private static final String XXstBuf[] = {
        "~(a[0]  | a[1]  | a[2]  | a[3]  | a[4]  | a[5]  | a[6]  | a[7]\n" +
"           | a[8]  | a[9]  | a[10] | a[11] | a[12] | a[13] | a[14] | a[15]\n" +
"           | a[16] | a[17] | a[18] | a[19] | a[20] | a[21] | a[22] | a[23]\n" +
"           | a[24] | a[25] | a[26] | a[27] | a[28] | a[29] | a[30] | a[31])"
    };

   @Test
    public void testAccepti() {
        System.out.println("accepti");
        for (String tt : stBuf) {
            test(tt);
        }
    }


        public static void test(final String tt) {
        System.out.print("fact: " + tt + ": parses as: ");
        CharBuffer buf = new CharBuffer("<test>", tt);
        State st = State.create(buf);
        boolean result;
        expression gram = new expression();
        Acceptor acc = gram.accept();
        if (null != acc) {
            String ss = acc.toString();
            System.out.println(ss);
        }
        result = (null != acc) && st.isEOF();
        assertTrue(result);
    }

}
