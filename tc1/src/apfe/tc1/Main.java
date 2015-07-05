package apfe.tc1;

import apfe.runtime.Acceptor;
import apfe.runtime.ParseError;
import apfe.runtime.ScannerState;
import apfe.runtime.State;
import apfe.tc1.generated.*;
import java.io.IOException;
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
            Tc1Scanner toks = new Tc1Scanner(fn);
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
                //gblib.Util.info(0, "APFE-STAT-1", stats[0], stats[1], pcnt);
                System.out.printf("Hit rate: (%d/%d): %.2f %%", stats[0], stats[1], pcnt);
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
