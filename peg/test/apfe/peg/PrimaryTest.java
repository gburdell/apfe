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
package apfe.peg;

import apfe.peg.generate.GenJava;
import apfe.runtime.CharBuffer;
import apfe.runtime.CharBufState;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gburdell
 */
public class PrimaryTest {

    private static final String stData[] = {
        "\"foobar\"", "'d'", "\"xyz\\\"\"", "'\\n'", ".",
        "ident1", "__ident_cls_", "_another__id_cls___",
        "(a b c)", "(a b c* / d e f ? fo_bar+ dog)"
    };

    /**
     * Test of genJava method, of class Primary.
     */
    @Test
    public void testGenJava() {
        Primary instance;
        System.out.println("genJava");
        CharBufState st;
        String res;
        for (String s : stData) {
            CharBuffer buf = new CharBuffer("<test>", s);
            st = CharBufState.create(buf);
            instance = new Primary();
            assertTrue(instance.acceptTrue());
            GenJava j = new GenJava();
            j = instance.genJava(j);
            res = j.toString();
            System.out.println("test: " + s + " returns: " + j);            
        }
    }
}
