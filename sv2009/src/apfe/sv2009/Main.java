package apfe.sv2009;

import apfe.runtime.Acceptor;
import apfe.runtime.ParseError;
import apfe.runtime.ScannerState;
import apfe.runtime.State;
import apfe.runtime.Token;
import apfe.runtime.Util;
import apfe.sv2009.generated.*;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
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
            addListeners();
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
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void addListeners() {
        module_identifier.addListener(new Acceptor.Listener() {

            @Override
            public void onAccept(Acceptor accepted) {
                Token id = ScannerState.getToken(accepted.getStartMark());
                String dbg1 = id.getText();
                dbg1 += "";
            }
        });
    }
    
    public static class ReaderThread extends Thread {
        public ReaderThread(PipedOutputStream src) {
            try {
                m_ins = new PipedInputStream(src);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }

        @Override
        public void run() {
            super.run(); //To change body of generated methods, choose Tools | Templates.
        }
        
        
        private PipedInputStream m_ins;
    }
}
