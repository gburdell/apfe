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
package apfe.peg;

import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer;
import apfe.runtime.CharBufState;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gburdell
 */
public class CommentTest {

    public CommentTest() {
    }
    private static final String stTry = "# this is a line comment\nthis is not";

    /**
     * Test of accepti method, of class Comment.
     */
    @Test
    public void testAccepti() {
        System.out.println("accepti");
        CharBuffer buf = new CharBuffer("<test>", stTry);
        CharBufState st = CharBufState.create(buf);
        Comment instance = new Comment();
        boolean expResult = true;
        boolean result = (null != (instance = Acceptor.match(instance)));
        assertEquals(expResult, result);
        //expect 3 ids
        Identifier id1;
        String s;
        final String ess[] = {"this","is","not"};
        for (int i = 0; i < 3; i++) {
            id1 = new Identifier();
            result = id1.acceptTrue();
            assertTrue(result);
            s = id1.toString();
            assertEquals(s, ess[i]);
        }
    }
}