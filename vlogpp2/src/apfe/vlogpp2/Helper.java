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
package apfe.vlogpp2;

import apfe.runtime.CharBuffer;
import apfe.runtime.Marker;
import static apfe.runtime.CharBuffer.NL;
import apfe.runtime.InputStream;
import apfe.runtime.Memoize;
import apfe.runtime.CharBufState;
import gblib.MessageMgr;
import gblib.Util;
import gblib.File;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

/**
 * Toplevel object/singleton for vlogpp system.
 *
 * @author gburdell
 */
public class Helper {

    private Helper() {
        String fname = System.getProperty("vlogpp.messages");
        if (null != fname) {
            MessageMgr.addMessages(fname);
        }
    }

    public static Helper getTheOne() {
        return stTheOne;
    }

    public static void warning(String code, Object... args) {
        Util.warn(code, args);
    }

    public static void error(String code, Object... args) {
        Util.error(code, args);
    }

    public static void info(String code, Object... args) {
        Util.info(code, args);
    }

    public static void info(int minLvl, String code, Object... args) {
        Util.info(minLvl, code, args);
    }

    /**
     * Entry point for running vlogpp.
     *
     * @param fname source file to process.
     * @return Grammar instance to run acceptor() (or null on error).
     */
    /*NOT USED for now
     public Grammar start(String fname) {
     CharBufState.clear();
     Grammar gram = null;
     m_fname = null;
     try {
     InputStream fins = new InputStream(fname);
     CharBuffer cbuf = fins.newCharBuffer();
     //Prepend `line
     StringBuilder pfx = new StringBuilder("`line 1 \"");
     pfx.append(fname).append("\" 0").append(NL);
     cbuf.getBuf().insert(0, pfx);
     CharBufState st = CharBufState.create(cbuf);
     m_fname = fname;
     gram = new Grammar();
     } catch (Exception ex) {
     //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
     error("VPP-FILE-1", fname, "read");
     }
     return gram;
     }
     */
    /**
     * Set filename we are currently processing.
     *
     * @param fname
     */
    public void setFname(final String fname) {
        m_fname = fname;
    }

    public String getFname() {
        return m_fname;
    }

    public MacroDefns getMacroDefns() {
        return m_macroDefns;
    }

    public IncludeDirs getIncludeDirs() {
        return m_inclDirs;
    }

    public static CharBuffer getBuf() {
        return CharBufState.asMe().getBuf();
    }

    /**
     * Replace current CharBuffer contents [start,current) with s. Optionally,
     * clear/invalidate any memoization too.
     *
     * @param start start position in buffer.
     * @param s string to stitch into buffer[start,current).
     * @param doReset do memoize reset. Normally true, but if we have series of
     * buffer steps, then just do on last one.
     */
    public void replace(final Marker start, String s, boolean doReset) {
        if ((null != s) && s.isEmpty()) {
            s = " ";
        }
        getBuf().replace(start, s);
        if (doReset) {
            Memoize.reset();
        }
    }

    /**
     * Replace current CharBuffer contents [start,current) with s.
     * Clear/invalidate any memoization too.
     *
     * @param start start position in buffer.
     * @param s string to stitch into buffer[start,current).
     */
    public void replace(final Marker start, String s) {
        replace(start, s, true);
    }

    /**
     * Replace current CharBuffer [start,current) with s.
     * Do not rollback buffer, so continue from current.
     * @param start start position in buffer.
     * @param s string to replace with.
     */
    public void replaceNoRollBack(final Marker start, String s) {
        final CharBuffer buf = getBuf();
        buf.replace(start, s, false);
    }

    /**
     * Replace current CharBuffer contents [start,current) with space, while
     * retaining any newline. Clear/invalidate any memoization too.
     *
     * @param start start position.
     */
    public void replace(final Marker start) {
        getBuf().replace(start);
        Memoize.reset();
    }

    public void reset(final Marker start) {
        reset(start, true);
    }

    public void reset(final Marker start, boolean doReset) {
        getBuf().reset(start);
        if (doReset) {
            Memoize.reset();
        }
    }

    public void reset(String fn, int lnum) {
        getBuf().reset(fn, lnum);
        Memoize.reset();
    }

    public static boolean stWarnOnMultipleInclude = true;

    /**
     * Get 1st valid include file by searching include dirs for fnm.
     *
     * @param loc location of filename (in `include stmt).
     * @param fnm include file to find.
     * @return first valid include file (or null).
     */
    public File getInclFile(Location loc, String fnm) {
        List<File> cands = m_inclDirs.findInclFile(fnm);
        if (stWarnOnMultipleInclude && (1 < cands.size())) {
            warning("VPP-INCL-1", loc, fnm, cands.size());
            int i = 1;
            for (File f : cands) {
                String absNm;
                absNm = f.getCanonicalPath();
                warning("VPP-INCL-2", i++, absNm);
            }
        }
        if (1 == cands.size()) {
            info(2, "VPP-INCL-5", cands.get(0).toString(), fnm);
        }
        return (0 < cands.size()) ? cands.get(0) : null;
    }

    /**
     * Add include directory to search path.
     *
     * @param dir include directory.
     * @return true on success, else false.
     */
    public boolean addInclDir(String dir) {
        return m_inclDirs.add(dir);
    }
    private final MacroDefns m_macroDefns = new MacroDefns();
    private final IncludeDirs m_inclDirs = IncludeDirs.create(".");
    private static final Helper stTheOne = new Helper();
    private String m_fname;

    private static enum IfdefState {

        eDone, //ifn?def clause taken
        eNotDone, //ifn?def not taken
        eBlockDone; //block until closing endif

        public boolean pass() {
            return (this == eDone);
        }
    }

    /**
     * Check state of conditional block pass.
     *
     * @return true if not blocked by conditional clause/block.
     */
    public boolean getConditionalAllow() {
        return (m_ifdefStack.isEmpty()) ? true : m_ifdefStack.peek().pass();
    }

    public boolean isDefined(String key, Location loc) {
        return m_macroDefns.isDefined(key, loc);
    }

    public boolean isDefined(String key) {
        return m_macroDefns.isDefined(key);
    }

    public void ticIfdef(String key, Location loc) {
        boolean rval = isDefined(key, loc);
        m_ifdefStack.push(next(rval ? IfdefState.eDone
                : IfdefState.eNotDone));
    }

    public void ticIfndef(String key, Location loc) {
        boolean rval = !isDefined(key, loc);
        m_ifdefStack.push(next(rval ? IfdefState.eDone
                : IfdefState.eNotDone));
    }

    public void ticElse() {
        IfdefState was = m_ifdefStack.pop();
        IfdefState nxt = (was != IfdefState.eNotDone)
                ? IfdefState.eBlockDone : IfdefState.eDone;
        m_ifdefStack.push(next(nxt));
    }

    public void ticElsif(String key, Location loc) {
        IfdefState was = m_ifdefStack.pop();
        IfdefState nxt = (was != IfdefState.eNotDone) ? IfdefState.eBlockDone
                : (isDefined(key, loc) ? IfdefState.eDone : IfdefState.eNotDone);
        m_ifdefStack.push(next(nxt));
    }

    public void ticEndif() {
        assert (!m_ifdefStack.empty());
        m_ifdefStack.pop();
    }

    /**
     * Alter next state if previous is block
     */
    private IfdefState next(IfdefState dflt) {
        IfdefState ns = dflt;
        if (!m_ifdefStack.empty()
                && (IfdefState.eDone != m_ifdefStack.peek())) {
            ns = IfdefState.eBlockDone;
        }
        return ns;
    }
    private final Stack<IfdefState> m_ifdefStack = new Stack<>();
}
