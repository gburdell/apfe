/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apfe.dsl.vlogpp.parser;

import apfe.runtime.CharBuffer;
import apfe.runtime.CharBufState;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gburdell
 */
public class ActualArgumentTest {

    public static String stBuf[] = new String[]{
        "arg1",
        "(arg2)",
        "(a && ((b != c) | d))",
        "(a&((b)&c)|d)",
        "{1'b0,gl_rt_msaa_gras}",
        "~(a != 3'b100 &&  ((b != `RB_1xMSAA) | c | d))",
        "~(a != 3'b100 &&  ((b != j) | c | d | e | ~f | g | h | i[1]))",
        "~(a != 3'b100 &&  ((b != `RB_1xMSAA) | c | d | e | ~f | g | h | i[1]))"
    };
    public static String XXstBuf[] = new String[]{
        "(a&((b)&c)|d)"
    };

    /**
     * Test of accepti method, of class ActualArgument.
     */
    @Test
    public void testAccepti() {
        for (String s : stBuf) {
            CharBufState.create(new CharBuffer("<stBuf>", s));
            ActualArgument aarg = new ActualArgument();
            boolean match = aarg.acceptTrue();
            System.out.println(s + ": parses as: " + aarg.toString());
            assertTrue(match && CharBufState.getTheOne().isEOF());
        }
    }

}
