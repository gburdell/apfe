package apfe.sv2009;

import apfe.dsl.vlogpp.Main.WriterThread;
import static apfe.sv2009.Main.process;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gburdell
 */
public class MtMain {
    /**
     * Run vlogpp and parser using multithread connected pipes.
     * @param argv arguments passed to vlogpp.
     */
    public static void main(String argv[]) {
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
        process(toks);
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
                m_ins.close();
            } catch (IOException ex) {
                Logger.getLogger(MtMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private PipedReader m_ins;
        private SvScanner m_toks;
    }
}
