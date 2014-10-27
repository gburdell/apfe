/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apfe.sv2009;

import apfe.runtime.Token;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gburdell
 */
public class SvScannerTest {

    private static final String stFn = "/home/gburdell/projects/vlog/tmp/top.postVloggp.v";

    @Test
    public void testSvScanner() {
        try {
            SvScanner lex = new SvScanner(stFn);
            if (false) {
                for (Token tok : lex) {
                    System.out.println(tok.toString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(SvScannerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
