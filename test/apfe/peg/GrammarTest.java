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
import apfe.runtime.State;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author gburdell
 */
public class GrammarTest {

    public GrammarTest() {
    }
    private static final String stExps[] = {
        "# Hierarchical syntax",
        "Grammar <- Spacing defn1=Definition+ EndOfFile",
        "Definition <- Identifier LEFTARROW expr=Expression",
        "  {{ /*stuff for Definition*/ }}  ",
        "Expression <- seq1=(Sequence (SLASH Sequence)*)",
        "Sequence <- Prefix*",
        "Prefix <- (AND / NOT)? Suffix",
        "Suffix <- Primary (QUESTION / STAR / PLUS)?",
        "Primary <- Identifier !LEFTARROW",
        "/  OPEN Expression CLOSE",
        "/  Literal / Class / DOT"
    };
    private static final String XXstExps[] = {
        "# Hierarchical syntax",
        "AA <- s1=bb+ cc",
        "dd <- ee ff",
        "ff<-gg*"
    };

    /**
     * Test of accepti method, of class Comment.
     */
    @Test
    public void testAccepti() {
        StringBuilder bld = new StringBuilder();
        for (String s : stExps) {
            bld.append(s).append("\n");
        }
        final char stBuf[] = bld.toString().toCharArray();
        final int stN = stBuf.length;
        System.out.println("accepti");
        CharBuffer buf = new CharBuffer("<test>", stBuf, stN);
        State st = State.create(buf);
        boolean result;
        Grammar gram = new Grammar();
        Acceptor acc = gram.accept();
        result = (null != acc);
        assertTrue(result);
        String ss = acc.toString();
        System.out.println("grammar:");
        System.err.println(ss);
    }
}