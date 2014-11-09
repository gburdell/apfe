/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apfe.peg.expr;

import org.junit.Test;

/**
 *
 * @author gburdell
 */
public class MainTest {

    public static final String stBuf[] = new String[]{
        "4*5+6-7+-9",
        "10*(20-4)/5",
        "666",
        "1+2*3",
        "(1+2)*3-(8*4*(45-23))/2"
    };

    @Test
    public void testMain() {
        Main.main(stBuf);
    }

}
