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
import apfe.peg.PegSequence;
import apfe.runtime.Util;
import static apfe.peg.generate.Main.getProperty;
import static apfe.peg.generate.Main.getPropertyAsBoolean;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
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

    private static String prepTmpl(String tmpl) {
        tmpl = Template.removeOrKeep(stGenListeners, tmpl,
                "@6.1@", "@6.2@", "@6.3@");
        tmpl = Template.removeOrKeep(stGenMemoize, tmpl,
                "@4.1@", "@4.2@");
        return tmpl;
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
        m_baseTmpl = prepTmpl(m_baseTmpl);
        m_dlrBaseTmpl = prepTmpl(m_dlrBaseTmpl);
        if (m_topLevelCls.isEmpty()) {
            if (Main.stGenMaze) {
                generateSeparateMaze();
            } else {
                generateSeparate();
            }
        } else {
            Util.assertFalse(Main.stGenMaze, "generateOne() not supported for maze yet");
            generateOne();
        }
    }

    /**
     * Quick-n-dirty approach to generate direct left-recursive with direct
     * right-recursive. Generate each PegSequence and then search for instance
     * of this definition on begin and tail (if isDRR), and replace with 'this'
     * (on first) and isDRR(true) on tail.
     *
     * @param gen code generator.
     * @param defn left-recursive Definition.
     */
    private GenJava getDlrSwitchClause(GenJava gen, final Definition defn) {
        Util.assertFalse(Main.stGenMaze, "getDlrSwitchClause() not supported for maze yet");
        final String nm = defn.getId().getId();
        final String toMatch = "new " + GenJava.getClsNm(nm) + "()";
        final String drrReplace = "new " + GenJava.getClsNm(nm) + "(true)";
        final String tmpl = "case @1@:\nmatcher = @2@ ;\nbreak;\n";
        Analyze.LRDetails lrd = m_anz.getLRDetailsByName().get(nm);
        List<PegSequence> seqs = defn.getExpr().getSequences();
        assert (null != seqs && (1 < seqs.size()));
        int ix = 0, beg, end;
        StringBuilder srep;    //string rep of alternative
        for (PegSequence s : seqs) {
            srep = (new GenJava()).append(s).getStringBuilder();
            if (ix < lrd.m_cnt) {
                //lr candidate
                beg = srep.indexOf(toMatch);
                assert (0 <= beg);
                end = beg + toMatch.length();
                srep.replace(beg, end, "this");
                if (lrd.m_hasDRR[ix]) {
                    beg = srep.lastIndexOf(toMatch);
                    assert (0 < beg);
                    end = beg + toMatch.length();
                    srep.replace(beg, end, drrReplace);
                }
            }
            gen = gen.template(tmpl, ix, srep.toString());
            ix++;
        }
        return gen;
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
            String dlrBaseTmpl = Template.delete(m_dlrBaseTmpl, "@9@");
            dlrBaseTmpl = Template.removeOrKeep(true, dlrBaseTmpl, "@10@");
            String contents = null;
            String baseNm, clsNm;
            for (Definition defn : m_anz.getDefnByName().values()) {
                if (null == defn || defn.isExtCls()) {
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
                    gen = getDlrSwitchClause(gen, defn);
                    contents = GenJava.replaceSpecd(dlrBaseTmpl,
                            "@11@", "static",
                            "@2@", clsNm,
                            "@3@", baseNm,
                            "@5@", gen.toString(),
                            "@4@", m_anz.getLRDetailsByName().get(nm).m_cnt
                    );
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

    private void generateSeparateMaze() {
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
            Util.assertFalse(defn.isExtCls(), nm + ": definition has external class");
            clsNm = GenJava.getClsNm(nm);
            String contents = Template.substring(m_baseTmpl,
                    defn.isToken() ? "@TERMINAL@" : "@NONTERMINAL@");
            if (defn.isToken()) {
                //Terminal
                String tokCode = nm.toUpperCase() + "_K";
                contents = GenJava.replaceSpecd(contents,
                        "@PACKAGE@", m_pkgNm,
                        "@CLASS@", clsNm,
                        "@TOKCODE@", tokCode
                );
            } else {
                //NonTerminal
                matcher = new GenJava();
                matcher = defn.getExpr().genJava(matcher);
                contents = GenJava.replaceSpecd(contents,
                        "@PACKAGE@", m_pkgNm,
                        "@CLASS@", clsNm,
                        "@EXPR@", matcher.toString()
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

    private void generateSeparate() {
        String contents = null;
        String baseNm, clsNm;
        GenJava matcher;
        for (Definition defn : m_anz.getDefnByName().values()) {
            if (null == defn || defn.isExtCls()) {
                continue;
            }
            if (defn.isToken()) {
                assert Main.stGenMaze;
                continue;
            }
            final String nm = defn.getId().getId();
            if (nm.equals("EOL") || nm.equals("EOF")) {
                continue;
            }
            clsNm = GenJava.getClsNm(nm);
            matcher = new GenJava();
            if (defn.getIsLeftRecursive()) {
                baseNm = getProperty("lrBaseCls");
                matcher = getDlrSwitchClause(matcher, defn);
                contents = GenJava.replaceSpecd(m_dlrBaseTmpl,
                        "@2@", clsNm,
                        "@3@", baseNm,
                        "@5@", matcher.toString(),
                        "@4@", m_anz.getLRDetailsByName().get(nm).m_cnt,
                        "@9@", "",
                        "@10@", "",
                        "@11@", "",
                        "@1@", m_pkgNm
                );
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
