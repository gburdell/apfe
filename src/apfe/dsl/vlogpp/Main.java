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
package apfe.dsl.vlogpp;

import apfe.runtime.CharBuffer.Marker;
import apfe.runtime.Memoize;
import apfe.runtime.MessageMgr;
import apfe.runtime.State;
import apfe.runtime.Util;
import java.io.File;
import java.util.List;
import java.util.Stack;

/**
 * Toplevel object/singleton for vlogpp system.
 *
 * @author gburdell
 */
public class Main {

    private Main() {
    }

    public static Main getTheOne() {
        return stTheOne;
    }

    public static void warning(String code, Object... args) {
        MessageMgr.message('W', code, args);
    }

    public static void error(String code, Object... args) {
        MessageMgr.message('E', code, args);
    }

    public MacroDefns getMacroDefns() {
        return m_macroDefns;
    }

    /**
     * Replace current CharBuffer contents [start,current) with s.
     * Clear/invalidate any memoization too.
     * @param start start position in buffer.
     * @param s string to stitch into buffer[start,current).
     */
    public static void replaceCharBuffer(final Marker start, String s) {
        if (s.isEmpty()) {
            s = " ";
        }
        State.getTheOne().getBuf().replace(start, s);
        Memoize.reset();
    }
    
    public static boolean stWarnOnMultipleInclude = true;
    
    /**
     * Get 1st valid include file by searching include dirs for fnm.
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
                warning("VPP-INCL-2", i++, f.getName());
            }
        }
        return (0 < cands.size()) ? cands.get(0) : null;
    }
    
    /**
     * Add include directory to search path.
     * @param dir include directory.
     * @return true on success, else false.
     */
    public boolean addInclDir(String dir) {
        return m_inclDirs.add(dir);
    }
    
    private MacroDefns m_macroDefns = new MacroDefns();
    
    private IncludeDirs m_inclDirs = IncludeDirs.create(".");

    private static Main stTheOne = new Main();

    private static enum IfdefState {

        eDone, eNotDone, eBlockDone;

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

    public boolean isDefined(String key) {
        return m_macroDefns.isDefined(key);
    }

    public void ticIfdef(String key) {
        boolean rval = isDefined(key);
        m_ifdefStack.push(next(rval ? IfdefState.eDone
                : IfdefState.eNotDone));
    }

    public void ticIfndef(String key) {
        boolean rval = !isDefined(key);
        m_ifdefStack.push(next(rval ? IfdefState.eDone
                : IfdefState.eNotDone));
    }

    public void ticElse() {
        IfdefState was = m_ifdefStack.pop();
        IfdefState nxt = (was == IfdefState.eDone)
                ? IfdefState.eBlockDone : IfdefState.eDone;
        m_ifdefStack.push(next(nxt));
    }

    public void ticElsif(String key) {
        IfdefState was = m_ifdefStack.pop();
        IfdefState nxt = (was == IfdefState.eDone) ? IfdefState.eBlockDone
                : (isDefined(key) ? IfdefState.eDone : IfdefState.eNotDone);
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

    private Stack<IfdefState> m_ifdefStack = new Stack<>();

    static {
        File f = new File(new File(Util.getToolRoot()), "messages.vlogpp.txt");
        MessageMgr.addMessages(f);
    }

}
