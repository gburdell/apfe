/*
 * The MIT License
 *
 * Copyright 2014 gburdell.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package apfe.peg.generate;

import apfe.peg.Definition;
import apfe.runtime.Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TODO: This is currently for Java generation. Will need to refactor a bit to
 * be more target agnostic.
 *
 * @author gburdell
 */
public class Generate {

    public static void generate(Analyze anz) {
        (new Generate(anz)).generate();
    }

    private Generate(Analyze anz) {
        m_anz = anz;
    }

    private void generate() {
        {   //read basic template
            String fn = Main.getProperty("baseJavaTemplate");
            m_baseTmpl = readWholeFile(fn);
        }
        {   //create output dir
            String odir = Main.getProperty("outputDir");
            m_outputDir = new File(odir);
            if (!m_outputDir.exists() && !m_outputDir.mkdirs()) {
                errorAndExit("DIR-1", odir, "create");
            }
        }
        final String allow[] = {"", ""}, notAllow[] = {"/*", "*/"};
        final String genListeners[] = (Main.getPropertyAsBoolean("genListeners"))
                ? allow : notAllow;
        final String genMemoize[] = (Main.getPropertyAsBoolean("genMemoize"))
                ? allow : notAllow;
        final String pkgNm = Main.getProperty("packageName");
        String contents = null;
        String baseNm, clsNm;
        GenJava matcher;
        for (Definition defn : m_anz.getDefnByName().values()) {
            if (null == defn) {
                continue;
            }
            final String nm = defn.getId().getId();
            if (nm.equals("EOL") || nm.equals("EOF")) {
                continue;
            }
            clsNm = GenJava.getClsNm(nm);
            matcher = new GenJava();
            if (defn.getIsLeftRecursive()) {
                //TODO
            } else {
                baseNm = Main.getProperty("baseCls");
                matcher = defn.getExpr().genJava(matcher);
                contents = GenJava.replace(m_baseTmpl,
                        /*1*/ pkgNm,
                        /*2*/ clsNm,
                        /*3*/ baseNm,
                        /*4*/ genMemoize[0],
                        /*5*/ genMemoize[1],
                        /*6*/ genListeners[0],
                        /*7*/ genListeners[1],
                        /*8*/ matcher.toString()
                );
            }
            final File outf = new File(m_outputDir, clsNm + ".java");
            try (PrintStream fos = new PrintStream(new FileOutputStream(outf))) {
                fos.println(contents);
            } catch (Exception ex) {
                errorAndExit("FILE-1", outf.toString());
            }
        }
    }

    private static String readWholeFile(final String fn) {
        String rval = null;
        try (Scanner scn = new Scanner(new File(fn))) {
            //trick to read whole file
            rval = scn.useDelimiter("\\A").next();
        } catch (IOException ex) {
            errorAndExit("FILE-2", fn);
        }
        return rval;
    }

    private static void errorAndExit(String code, Object... args) {
        Util.error(code, args);
        System.exit(1);
    }

    private File m_outputDir;
    private final Analyze m_anz;
    private String m_baseTmpl;
}
