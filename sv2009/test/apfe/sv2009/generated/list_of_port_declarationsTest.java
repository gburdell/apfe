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
import apfe.runtime.ParseError;
import apfe.runtime.State;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author gburdell
 */
public class list_of_port_declarationsTest {

    private static final String stBuf[] = {
        "( input wire clk, output wire z, t.u v)",
        //"output reg x[3:0]"
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
        list_of_port_declarations  gram = new list_of_port_declarations();
        Acceptor acc = gram.accept();
        if (null != acc) {
            String ss = acc.toString();
            System.out.println(ss);
        }
        result = (null != acc) && st.isEOF();
        if (!result) {
            ParseError.printTopMessage();
        }
        Assert.assertTrue(result);
    }

}
