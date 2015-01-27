package apfe.sv2009;

import apfe.vlogpp2.WriterThread;
import apfe.runtime.State;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import gblib.Timer;
import static gblib.Util.error;
import static gblib.Util.info;
import static gblib.MessageMgr.getErrorCnt;
import gblib.Util;

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
        State.clear(); //reset and load (messages)
        Timer timer = new Timer();
        //since we're re-entrant, track if any new errors introduced by 
        //vlogpp/token part
        int startErrCnt = getErrorCnt();
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
            Util.abnormalExit(ex);
        }
        info(2, "TIM-1", "vlogpp + tokenizer", timer.toString());
        int errCnt = getErrorCnt();
        //check for errors during vlogpp
        if (0 < (errCnt - startErrCnt)) {
            error("VPP-EXIT");
            System.exit(errCnt);
        }
        //clear out apfe state used by vlogpp
        State.clear();
        timer.restart();
        info(2, "PARSE-0");
        apfe.sv2009.Main.process(toks);
        info(2, "TIM-1", "parsing", timer.toString());
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
