package apfe.laol.generated;

import apfe.runtime.Acceptor;
import apfe.runtime.CharBufState;
import apfe.runtime.CharBuffer;
import apfe.runtime.InputStream;
import apfe.runtime.ParseError;
import gblib.Util;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gburdell
 */
public class AssignmentExpressionTest {

    public AssignmentExpressionTest() {

    }

    /**
     * Test of accepti method, of class AssignmentExpression.
     */
    @Test
    @SuppressWarnings("CallToPrintStackTrace")
    public void testAccepti() {
        final String fn = "test/data/t1.txt";
        try {
            InputStream fis = new InputStream(fn);
            CharBuffer cbuf = fis.newCharBuffer();
            CharBufState st = CharBufState.create(cbuf);
            Grammar gram = new Grammar();
            Acceptor acc = gram.accept();
            if (null != acc) {
                String ss = acc.toString();
                System.out.println("returns:\n========\n" + ss);
            }
            boolean result = (null != acc) && CharBufState.getTheOne().isEOF();
            if (!result) {
                ParseError.printTopMessage();
            }
            assertTrue(result);
        } catch (Exception ex) {
            Util.abnormalExit(ex);
        }
    }

}
