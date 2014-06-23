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
import apfe.runtime.InputStream;
import apfe.runtime.ParseError;
import apfe.runtime.State;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gburdell
 */
public class GrammarTest {
    static String stFname = "/home/gburdell/projects/apfe/test/apfe/dsl/vlogpp/t1.vh";
    /**
     * Test of accepti method, of class Grammar.
     */
    @Test
    public void testAccepti() {
        try {
            final String fn = stFname;
            System.out.println("accepti");
            InputStream fins = new InputStream(fn);
            CharBuffer cbuf = fins.newCharBuffer();
            State st = State.create(cbuf);
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
            assertTrue(result);
        } catch (Exception ex) {
            Logger.getLogger(GrammarTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
