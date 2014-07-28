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
import apfe.peg.Expression;
import apfe.runtime.Util;
import static apfe.peg.generate.Main.getProperty;
import static apfe.peg.generate.Main.getPropertyAsBoolean;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

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
        {   //read templates
            String fn = getProperty("baseJavaTemplate");
            m_baseTmpl = readWholeFile(fn);
            fn = getProperty("dlrBaseJavaTemplate");
            m_dlrBaseTmpl = readWholeFile(fn);
        }
        {   //create output dir
            String odir = getProperty("outputDir");
            m_outputDir = new File(odir);
            if (!m_outputDir.exists() && !m_outputDir.mkdirs()) {
                errorAndExit("DIR-1", odir, "create");
            }
        }
        //adjust template
        m_baseTmpl = Template.removeOrKeep(stGenListeners, m_baseTmpl,
                "@6.1@", "@6.2@", "@6.3@");
        m_baseTmpl = Template.removeOrKeep(stGenMemoize, m_baseTmpl,
                "@4.1@", "@4.2@");
        if (m_topLevelCls.isEmpty()) {
            generateSeparate();
        } else {
            generateOne();
        }
    }
    
    private void getDlrSwitchClause(GenJava gen, final Definition defn) {
        final String nm = defn.getId().getId();
        Analyze.LRDetails lrd = m_anz.getLRDetailsByName().get(nm);
        gen.beginDLR(lrd);
        Expression expr = defn.getExpr();
        gen = expr.genJava(gen);
        gen.endDLR();
        //TODO
    }
    
    private void generateOne() {
        final File outf = new File(m_outputDir, m_topLevelCls + ".java");
        try (PrintStream fos = new PrintStream(new FileOutputStream(outf))) {
            //dump outermost so strip out class
            String baseTmpl = Template.delete(m_baseTmpl, "@10@");
            GenJava gen = new GenJava();
            gen.templateSpecd(baseTmpl,
                    "@1@", m_pkgNm,
                    "@9@", ""
            ).append("public class ")
                    .append(m_topLevelCls)
                    .append(" {");
            fos.println(gen.toString());
            //new template for static inner class
            baseTmpl = Template.delete(m_baseTmpl, "@9@");
            baseTmpl = Template.removeOrKeep(true, baseTmpl, "@10@");
            //new template for dlr static inner class
            String dlrBaseTmpl = Template.delete(m_baseTmpl, "@9@");
            dlrBaseTmpl = Template.removeOrKeep(true, dlrBaseTmpl, "@10@");
            String contents = null;
            String baseNm, clsNm;
            for (Definition defn : m_anz.getDefnByName().values()) {
                if (null == defn) {
                    continue;
                }
                final String nm = defn.getId().getId();
                if (nm.equals("EOL") || nm.equals("EOF")) {
                    continue;
                }
                clsNm = GenJava.getClsNm(nm);
                gen = new GenJava();
                if (defn.getIsLeftRecursive()) {
                    baseNm = getProperty("lrBaseCls");
                    getDlrSwitchClause(gen, defn);
                } else {
                    baseNm = getProperty("baseCls");
                    gen = defn.getExpr().genJava(gen);
                    contents = GenJava.replaceSpecd(baseTmpl,
                            "@11@", "static",
                            "@2@", clsNm,
                            "@3@", baseNm,
                            "@8@", gen.toString()
                    );
                }
                fos.println(contents);
            }
            fos.println("}");
        } catch (Exception ex) {
            errorAndExit("FILE-1", outf.toString());
        }
    }

    private void generateSeparate() {
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
                baseNm = getProperty("baseCls");
                matcher = defn.getExpr().genJava(matcher);
                contents = GenJava.replaceSpecd(m_baseTmpl,
                        "@1@", m_pkgNm,
                        "@2@", clsNm,
                        "@3@", baseNm,
                        "@8@", matcher.toString(),
                        "@9@", "",
                        "@10@", "",
                        "@11@", ""
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

    public static String readWholeFile(final String fn) {
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
    private String m_baseTmpl, m_dlrBaseTmpl;
    private static final boolean stGenListeners = getPropertyAsBoolean("genListeners");
    private static final boolean stGenMemoize = getPropertyAsBoolean("genMemoize");
    private final String m_pkgNm = Main.getProperty("packageName");
    private final String m_topLevelCls = Main.getProperty("topLevelClsNm");
}
