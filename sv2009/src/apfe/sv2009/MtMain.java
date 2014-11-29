package apfe.sv2009;

import apfe.dsl.vlogpp.WriterThread;
import apfe.runtime.State;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import static gblib.Util.error;
import static gblib.MessageMgr.getErrorCnt;

/**
 *
 * @author gburdell
 */
public class MtMain {
    public static void main(String argv[]) {
        process(argv);
    }
    /**
     * Run vlogpp and parser using multithread connected pipes.
     * @param argv arguments passed to vlogpp.
     */
    public static void process(String argv[]) {
        SvScanner toks = null;
        try {
            WriterThread vlogpp = new WriterThread(argv);
            ReaderThread tokenizer = new ReaderThread(vlogpp.getWriter());
            vlogpp.start();
            tokenizer.start();
            //wait for finish
            vlogpp.join();
            tokenizer.join();
            toks = tokenizer.getToks();
        } catch (InterruptedException ex) {
            Logger.getLogger(MtMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        int errCnt = getErrorCnt();
        if (0 < errCnt) {
            error("VPP-EXIT");
            System.exit(errCnt);
        }
        //clear out apfe state used by vlogpp
        State.clear();
        apfe.sv2009.Main.process(toks);
    }

    private static class ReaderThread extends Thread {
        public SvScanner getToks() {
            return m_toks;
        }
        public static int stBufSz = 1 << 24; //16M?

        public ReaderThread(PipedWriter src) {
            try {
                m_ins = new PipedReader(src, stBufSz);
                m_toks = new SvScanner(m_ins);
            } catch (IOException ex) {
                Logger.getLogger(MtMain.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(MtMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private PipedReader m_ins;
        private SvScanner m_toks;
    }
}
