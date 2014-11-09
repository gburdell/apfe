//The MIT License
//
//Copyright (c) 2006-2010  Karl W. Pfalzer
//Copyright (c) 2011-      George P. Burdell
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in
//all copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//THE SOFTWARE.
package apfe.dsl.vlogpp;

import apfe.dsl.vlogpp.ProcArgs.Spec.EType;
import apfe.dsl.vlogpp.parser.Grammar;
import apfe.runtime.Acceptor;
import apfe.runtime.ParseError;
import apfe.runtime.CharBufState;
import static apfe.runtime.Util.asEmpty;
import static apfe.runtime.Util.nl;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author karl
 */
public class Main {

    private void init(String argv[]) {
        if (1 > argv.length) {
            usageErr(null);
        }
        try {
            m_args = new Args(argv);
            parse();
        } catch (ProcArgs.ArgException ex) {
            usageErr(ex.toString());
        }
    }

    public Main(String argv[]) {
        this(argv, new PrintWriter(System.out, true));
    }

    public Main(String argv[], PrintWriter os) {
        m_os = os;
        init(argv);
    }

    public static void main(String argv[]) {
        Main notUsed = new Main(argv);
    }

    private static void usageErr(String msg) {
        if (null != msg) {
            System.err.println("Error: " + msg);
        }
        usage();
        System.exit(1);
    }

    private static void usage() {
        final String progName = "parser.sv.Main";
        final String usage
                = "Usage: " + progName + " [options] infile..." + nl()
                + nl()
                + "Options:" + nl()
                + nl()
                + "  -I <dir>    Add <dir> (must be readable) to `include file search path." + nl()
                + "" + nl()
                + "  -D name     Predefine name as a macro with value 1." + nl()
                + "" + nl()
                + "  -D name=defn    Predefine name as a macro with value defn." + nl()
                + "" + nl()
                + "  -f cmdfile  \"cmdfile\" contains -I, -D and/or infile options." + nl();
        System.err.println(usage);
    }

    private void parse() {
        m_srcFiles = m_args.getOptVals(".");
        List<String> inclDirs = m_args.getOptVals("-I");
        List<Pair<String, String>> defs = Pair.factory(m_args.getOptVals("-D"));
        Helper hlp = Helper.getTheOne();
        for (String d : inclDirs) {
            hlp.addInclDir(d);
        }
        MacroDefns macs = hlp.getMacroDefns();
        for (Pair<String, String> md : defs) {
            macs.add(md.v1, md.v2, Location.stCmdLine);
        }
        parse(m_srcFiles);
    }

    private void parse(final List<String> srcs) {
        PrintWriter vpp = null;
        if (null != stDumpVpp) {
            try {
                System.err.println("DEBUG: generate vpp output: " + stDumpVpp);
                vpp = new PrintWriter(stDumpVpp);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (String fn : srcs) {
            Helper.info("VPP-PROC", fn);
            Grammar gram = Helper.getTheOne().start(fn);
            Acceptor acc = gram.accept();
            if (null != acc) {
                StringBuilder sb = new StringBuilder();//("`line 1 \"" + fn + "\" 0\n");
                sb.append(acc.toString());
                /*
                 System.out.println(super.getClass().getName()
                 + " returns:\n========\n" + ss);
                 */
                m_os.println(sb.toString());
                if (null != vpp) {
                    vpp.println(sb.toString());
                }
            }
            boolean result = (null != acc) && CharBufState.getTheOne().isEOF();
            if (!result) {
                ParseError.printTopMessage();
            }
        }
        if (null != vpp) {
            vpp.close();
        }
    }

    private Args m_args;
    private List<String> m_srcFiles;
    private final PrintWriter m_os;

    public static class WriterThread extends Thread {

        public WriterThread(String argv[]) {
            m_argv = argv;
            m_pipedWriter = new PipedWriter();
            m_os = new PrintWriter(m_pipedWriter, true);
        }

        public PipedWriter getWriter() {
            return m_pipedWriter;
        }

        @Override
        public void run() {
            try {
                Main notUsed = new Main(m_argv, m_os);
                m_pipedWriter.close();
                m_os.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private final String m_argv[];
        private final PipedWriter m_pipedWriter;
        private final PrintWriter m_os;
    }

    private static class Args extends ProcArgs {

        public Args(final String argv[]) throws ArgException {
            this.add(new ProcArgs.Spec("-I*", EType.eReadableDir))
                    .add(new ProcArgs.Spec("-D*", EType.eString))
                    .add(new ProcArgs.Spec(".+", EType.eReadableFile)) //filenames
                    ;
            if ((2 == argv.length) && argv[0].equals("-f")) {
                m_args = parse(argv[1]);
            } else {
                m_args = parse(argv);
            }
        }

        public boolean hasOpt(String opt) {
            return (null != m_args) ? m_args.containsKey(opt) : false;
        }

        public String getOptVal(String opt) {
            String rval = null;
            List<String> vals = getOptVals(opt);
            if (null != vals) {
                assert (1 == vals.size());
                rval = vals.get(0);
            }
            return rval;
        }

        public List<String> getOptVals(String opt) {
            return asEmpty(m_args.get(opt), new LinkedList<String>());
        }

        private final Map<String, List<String>> m_args;
    }

    private static final String stDumpVpp
            = System.getProperty("vlogpp.dumpVpp");

}
