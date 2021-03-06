package apfe.sv2009;

import apfe.runtime.Acceptor;
import apfe.runtime.ParseError;
import apfe.runtime.ScannerState;
import apfe.runtime.State;
import apfe.runtime.Token;
import apfe.sv2009.generated.*;
import gblib.Util;
import static gblib.Util.error;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 *
 * @author gburdell
 */
public class Main {

    /**
     * Run parser with single post-vlogpp'd file.
     *
     * @param argv single (post vlogpp) file.
     */
    public static void main(String argv[]) {
        try {
            final String fn = argv[0];
            SvScanner toks = new SvScanner(fn);
            process(toks);
        } catch (IOException ex) {
            Util.abnormalExit(ex);
        }
    }

    /*package*/ static void process(SvScanner toks) {
        State st = ScannerState.create(toks);
        //System.out.println("parser...");
        Grammar gram = new Grammar();
        addListeners();
        Acceptor acc = gram.accept();
        /*if (null != acc) {
         String ss = acc.toString();
         System.out.println("returns:\n========\n" + ss);
         }*/
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
            gblib.Util.info(3, "APFE-STAT-1", stats[0], stats[1], pcnt);
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

        public SvScanner getToks() {
            return m_toks;
        }
        public static int stBufSz = 1 << 24; //16M?

        public ReaderThread(PipedWriter src) {
            try {
                m_ins = new PipedReader(src, stBufSz);
                m_toks = new SvScanner(m_ins);
            } catch (IOException ex) {
                Util.abnormalExit(ex);
            }
        }

        @Override
        public void run() {
            try {
                m_toks.slurp();
            } catch (RuntimeException ex) {
                error("LEXER-1", ex.getMessage());
            }
            try {
                m_ins.close();
            } catch (IOException ex) {
                Util.abnormalExit(ex);
            }
        }

        private PipedReader m_ins;
        private SvScanner m_toks;
    }
}
