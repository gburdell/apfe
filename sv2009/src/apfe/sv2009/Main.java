/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apfe.sv2009;

import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer;
import apfe.runtime.InputStream;
import apfe.runtime.ParseError;
import apfe.runtime.ScannerState;
import apfe.runtime.State;
import apfe.runtime.Util;
import apfe.sv2009.generated.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gburdell
 */
public class Main {

    public static void main(String argv[]) {
        try {
            final String fn = argv[0];
            SvScanner toks = new SvScanner(fn);
            State st = ScannerState.create(toks);
            System.out.println("accepti");
            Grammar gram = new Grammar();
            Acceptor acc = gram.accept();
            if (null != acc) {
                String ss = acc.toString();
                System.out.println("returns:\n========\n" + ss);
            }
            boolean result = (null != acc) && State.getTheOne().isEOF();
            if (!result) {
                ParseError.printTopMessage();
            }
            {
                //dump memoize stats
                long stats[] = State.getTheOne().getMemoizeStats();
                double pcnt = 0;
                if (0 < stats[1]) {
                    pcnt = (100.0 * stats[0]) / stats[1];
                }
                Util.info("STAT-1", stats[0], stats[1], pcnt);
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
