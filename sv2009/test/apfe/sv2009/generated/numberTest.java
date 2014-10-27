package apfe.sv2009.generated;

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
import apfe.runtime.CharBufState;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gburdell
 */
public class numberTest {
     private static final String stBuf[] = {
        "4'b001", "12", "'b1", "12'h23"
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
        CharBufState st = CharBufState.create(buf);
        boolean result;
        number gram = new number();
        Acceptor acc = gram.accept();
        if (null != acc) {
            String ss = acc.toString();
            System.out.println(ss);
        }
        result = (null != acc) && st.isEOF();
        assertTrue(result);
    }

}
