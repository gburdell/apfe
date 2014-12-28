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
package apfe.vlogpp2;

import gblib.Pair;
import apfe.vlogpp2.ProcArgs.Spec.EType;
import gblib.Util;
import static gblib.Util.asEmpty;
import static gblib.Util.nl;
import static gblib.Util.info;
import static gblib.Util.appendIfNotNull;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
public class VppMain {

    private boolean init(String argv[]) {
        boolean ok = false;
        if (1 > argv.length) {
            usageErr(null);
        }
        try {
            m_args = new Args(argv);
            ok = parse();
        } catch (ProcArgs.ArgException ex) {
            usageErr(ex.toString());
        }
        return ok;
    }

    public VppMain(String argv[]) {
        this(argv, new PrintWriter(System.out, true));
    }

    public VppMain(String argv[], PrintWriter os) {
        m_os = os;
        init(argv);
    }

    public static void main(String argv[]) {
        VppMain notUsed = new VppMain(argv);
    }

    private static void usageErr(String msg) {
        if (null != msg) {
            System.err.println("Error: " + msg);
        }
        usage();
        System.exit(1);
    }

    private static void usage() {
        final String progName = "apfe.dsl.vlogpp2.VppMain";
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

    private boolean parse() {
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
        {
            //dump options
            for (Pair<String, String> md : defs) {
                info(3, "VPP-CLARG-1", "-D", appendIfNotNull(md.v1, "=", md.v2));
            }
            for (String incdir : inclDirs) {
                info(3, "VPP-CLARG-1", "-I", incdir); 
            }
            for (String src : m_srcFiles) {
                info(3, "VPP-CLARG-1", src, "");
            }
        }
        return parse(m_srcFiles);
    }

    private boolean parse(final List<String> srcs) {
        boolean ok = false;
        PrintWriter vpp = null;
        if (null != stDumpVpp) {
            try {
                Helper.info(1, "VPPE-1", stDumpVpp);
                //append to existing file
                vpp = new PrintWriter(new FileWriter(stDumpVpp,true));
            } catch (IOException ex) {
                Util.abnormalExit(ex);
            }
        }
        for (String fn : srcs) {
            Helper.info(2, "VPP-PROC", fn);
            IncludeDirs.setCurrentDir(fn);
            ok = (null == vpp) ? Parser.parse(fn, m_os) : Parser.parse(fn, m_os, vpp);
            IncludeDirs.resetCurrentDir();
            if (!ok) {
                break;
            }
        }
        if (null != vpp) {
            vpp.close();
        }
        return ok;
    }

    private Args m_args;
    private List<String> m_srcFiles;
    private final PrintWriter m_os;

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

    public static void setDumpVpp(String fname) {
        stDumpVpp = fname;
    }
    
    private static String stDumpVpp
            = System.getProperty("vlogpp.dumpVpp");

}