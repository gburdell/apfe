/*
 * The MIT License
 *
 * Copyright 2015 gburdell.
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
package apfe.v2.vlogpp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import static apfe.v2.vlogpp.FileCharReader.NL;
import gblib.Util.Pair;
import static gblib.Util.escape;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SystemVerilog source file.
 *
 * @author gburdell
 */
public class SourceFile {

    public static void main(final String argv[]) {
        for (final String arg : argv) {
            process(arg);
        }
    }

    private static void process(final String fname) {
        try {
            final SourceFile src = new SourceFile(fname, System.out);
            src.parse();
        } catch (FileNotFoundException | ParseError ex) {
            Logger.getLogger(SourceFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SourceFile(final String fname, final PrintStream os) throws FileNotFoundException {
        m_os = os;
        push(fname, 0);
    }

    /**
     * Match line contents against pattern. If >0 group(s) matched, save them.
     * If all groups matched, then return true. In order to match pattern, it is
     * assumed the patt is of form "(p)(q)?". In that example, cnt specified as
     * 2: indicating q must be matched. Need this convention since doesn't
     * appear to be method to match as much as possible w/o matching all.
     *
     * @param patt pattern to match.
     * @param cnt number of groups expected.
     * @return true if all groups matched; false if 0.
     * @throws ParseError if less than cnt group(s) matched.
     */
    private boolean match(final Pattern patt, final int cnt) throws ParseError {
        m_matcher = patt.matcher(m_str);
        if (!m_matcher.lookingAt()) {
            return false;
        }
        int groupCnt = m_matcher.groupCount();
        final FileLocation start = getFileLocation();
        FileLocation loc;
        int n;
        for (int i = 1; i <= groupCnt; i++) {
            n = m_matcher.start(i);
            if (0 > n) {
                groupCnt = i - 1;
                break;
            }
            loc = start.offset(n);
            getMatched().add(new Pair<>(loc, m_matcher.group(i)));
        }
        assert getMatched().size() == groupCnt;
        accept(m_matcher.end(groupCnt));
        if (groupCnt != cnt) {
            syntaxError("VPP-SYNTAX-2");
        }
        return true;
    }

    private int getMatchedGroupCnt() {
        return m_matcher.groupCount();
    }

    boolean matches(final Pattern patt) {
        return matches(patt, m_str);
    }

    private boolean matches(final Pattern patt, final String str) {
        m_matcher = patt.matcher(str);
        //match begin of pattern to being of line (not require entire region).
        return m_matcher.lookingAt();
    }

    void acceptMatch(final int group, final boolean save) {
        if (save) {
            getMatched().add(new Pair<>(getFileLocation(), m_matcher.group(group)));
        }
        int end = m_matcher.end(group);
        accept(end);
    }

    void acceptMatch(final int group) {
        acceptMatch(group, false);
    }

    private void printAcceptMatch(final int group) {
        print(m_matcher.group(group));
        acceptMatch(group);
    }

    void acceptMatchSave(final int group) {
        acceptMatch(group, true);
    }

    Queue<Pair<FileLocation, String>> getMatched() {
        return m_matched;
    }

    //reuse non-capturing whitespace w/ block-comment
    static final String stNCWS = "(?:[ \t]|/\\*.*?\\*/)+";
    //reuse time unit
    private static final String stTU = "(10?0?\\s*[munpf]s)";
    private static final Pattern stSpacePatt = Pattern.compile("([ \t]+)[^ \t]");
    private static final Pattern stUndef
            = Pattern.compile("(`undef(?=\\W))(" + stNCWS + "([a-zA-Z_]\\w*))?");
    private static final Pattern stTimescale
            = Pattern.compile("(`timescale(?=\\W))(" + stNCWS + stTU + "[ \t]*/[ \t]*" + stTU + "\\W)?");
    private static final Pattern stDefaultNetType
            = Pattern.compile("(`default_nettype(?=\\W))(" + stNCWS + "([a-z]\\w+)\\W)?");
    private static final Pattern stNetType
            = Pattern.compile("u?wire|tri[01]?|w(and|or)|tri(and|or|reg)|none");
    private static final Pattern stTicInclude 
            = Pattern.compile("(`include(?=\\W))(" + stNCWS 
                    + "(\\\"([^\\\"]+)(\\\"))|(<[^>]+(>)))?");


    private void syntaxError() throws ParseError {
        throw new ParseError("VPP-SYNTAX-1", m_is, escape(m_str.charAt(0)));
    }

    private void syntaxError(final String code) throws ParseError {
        final String tok = getMatched().peek().e2;
        getMatched().clear();
        throw new ParseError(code, getFileLocation(), tok, m_is.remainder());
    }

    public boolean parse() throws ParseError {
        boolean ok = true;
        char c;
        ParseError error = null;
        int ngrp;
        while (null != m_is) {
            while (!isEOF()) {
                try {
                    if (acceptOnMatch("\"")) {
                        stringLiteral();
                    } else if (acceptOnMatch("/*")) {
                        blockComment();
                    } else if (acceptOnMatch("//")) {
                        lineComment();
                    } else {
                        m_str = remainder();
                        if (matches(stSpacePatt)) {
                            printAcceptMatch(1);
                        } else if (match(TicDefine.stPatt, 3)) {
                            final TicDefine defn = TicDefine.process(this);
                            if (getEchoOn()) {
                                addDefn(defn);
                            }
                        } else if (match(TicConditional.stIfxdefElsif, 3)) {
                            final boolean echo = TicConditional.process(this);
                            setEchoOn(echo);
                        } else if (matches(TicConditional.stElseEndif)) {
                            acceptMatchSave(1);
                            final boolean echo = TicConditional.process(this);
                            setEchoOn(echo);
                        } else if (match(stTicInclude, 5)) {
                            final String fileNm = removeMatched(4).e2;
                            getMatched().clear();
                            //TODO
                        } else if (match(stUndef, 3)) {
                            final String macNm = removeMatched(2).e2;
                            getMatched().clear();
                            if (getEchoOn()) {
                                undef(macNm);
                            }
                        } else if (match(stTimescale, 4)) {
                            //don't really need to do anything w/ this for vpp??
                            //if so, use 3rd and 4th (1-origin)
                            getMatched().clear();
                        } else if (match(stDefaultNetType, 3)) {
                            final Pair<FileLocation, String> type = removeMatched(2);
                            if (!matches(stNetType, type.e2)) {
                                throw new ParseError("VPP-NETTYPE-1", type.e1, type.e2);
                            }
                            //do nothing
                            getMatched().clear();
                        } else if (TicDirective.process(this, m_str)) {
                            //do nothing
                        } else {
                            next();
                        }
                    }
                } catch (final ParseError pe) {
                    error = pe;
                    break;
                }
            }
            try {
                m_is.close();
            } catch (IOException ex) {
                Logger.getLogger(SourceFile.class.getName()).log(Level.SEVERE, null, ex);
                error = new ParseError("VPP-EXIT");
            }
            if (null != error) {
                throw error;
            }
            m_is = m_files.empty() ? null : m_files.pop();
        }
        return ok;
    }

    private Pair<FileLocation, String> removeMatched(int n) {
        assert 0 < n;
        Pair<FileLocation, String> r = null;
        for (; n > 0; n--) {
            r = getMatched().remove();
        }
        return r;
    }

    void replace(final int[] span, final String repl) {
        m_is.replace(span, repl);
    }

    String remainder() {
        return m_is.remainder();
    }

    private boolean isEnabled() {
        return true;
    }

    private void addDefn(final TicDefine defn) throws ParseError {
        if (null == m_macros) {
            m_macros = new MacroDefns();
        }
        m_macros.addDefn(defn);
    }

    boolean hasDefn(final String macroNm) {
        return (null == m_macros) ? false : m_macros.hasDefn(macroNm);
    }

    void accept(final int n) {
        m_is.accept(n);
    }

    int la(final int n) {
        return m_is.la(n);
    }

    int la() {
        return la(0);
    }

    boolean skipWhiteSpace() {
        while (0 <= " \t".indexOf(la())) {
            next();
        }
        return isEOF();
    }

    public boolean isEOF() {
        return m_is.isEOF();
    }

    public boolean isEOL() {
        return m_is.isEOL();
    }

    private void blockComment() throws ParseError {
        while (!isEOF()) {
            if (acceptOnMatch("*/")) {
                return;
            } else if (acceptOnMatch("/*")) {
                throw new ParseError("VPP-CMNT-2", m_is);
            }
            next();
        }
        if (isEOF()) {
            throw new ParseError("VPP-EOF-1", m_is, "<block-comment>");
        }
    }

    private void lineComment() throws ParseError {
        //slurp entire line
        final String rem = remainder();
        accept(rem.length());
        print(rem);
    }

    int[] getStartMark() {
        return m_is.getLineColNum();
    }

    /**
     * IEEE Std 1800-2012 5.9 String literals A string literal is a sequence of
     * characters enclosed by double quotes (""). Nonprinting and other special
     * characters are preceded with a backslash. A string literal shall be
     * contained in a single line unless the new line is immediately preceded by
     * a \ (backslash). In this case, the backslash and the newline are ignored.
     * There is no predefined limit to the length of a string literal.
     */
    void stringLiteral() throws ParseError {
        char c;
        final int started[] = getStartMark();
        boolean isUnterminated = false;
        while (!isUnterminated && !isEOF()) {
            if (acceptOnMatch("\\\n")) {
                //do nothing                
            } else {
                c = (char) la();
                switch (c) {
                    case '\\':  //escaped char
                        print(m_is.substring(2));
                        accept(2);
                        break;
                    case '"':
                        next();
                        return;
                    case NL:
                        isUnterminated = true;
                        break;
                    default:
                        next();
                }
            }
        }
        if (isUnterminated || isEOF()) {
            //VPP-STRING %s: unterminated string (started at %d:%d (line:col))
            throw new ParseError("VPP-STRING", m_is, started[0], started[1]);
        }
    }

    private boolean acceptOnMatch(final String s) {
        final boolean match = m_is.acceptOnMatch(s);
        if (match) {
            print(s);
        }
        return match;
    }

    int next() {
        final int c = (char) m_is.next();
        print((char) c);
        return c;
    }

    private void push(final String fname, final int lvl) throws FileNotFoundException {
        m_is = new FileCharReader(fname);
        if (0 <= lvl) {
            assert 2 >= lvl;
            //include file would be on non-empty stack
            m_os.printf("`line %d \"%s\" %d\n", m_is.getLineNum(), m_is.getFile().getCanonicalPath(), lvl);
        }
        m_files.push(m_is);
    }

    private void print(final char c) {
        if (m_echoOn) {
            m_os.print(c);
        }
    }

    private void print(final String s) {
        if (m_echoOn) {
            m_os.print(s);
        }
    }

    /**
     * Allow our friendly class like TicConditional to forcePrint, as necessary
     * to keep line counts in sync.
     *
     * @param c
     */
    void printNL() {
        m_os.print(NL);
    }

    boolean setEchoOn(final boolean val) {
        final boolean was = m_echoOn;
        m_echoOn = val;
        return was;
    }

    String getLocation() {
        return m_is.getLocation();
    }

    FileLocation getFileLocation() {
        return new FileLocation(m_is.getFile(), m_is.getLineNum(), m_is.getColNum());
    }

    void undef(final String macroNm) {
        if (null != m_macros) {
            m_macros.undef(m_is.getLocation(), macroNm);
        } else if (MacroDefns.getStrictness(MacroDefns.EStrictness.eUndefWarning)) {
            Messages.message('W', "VPP-UNDEF-1", m_is.getLocation(), macroNm, macroNm);
        }
    }

    boolean getEchoOn() {
        return m_echoOn;
    }

    private final Stack<FileCharReader> m_files = new Stack<>();
    private final PrintStream m_os;
    private FileCharReader m_is;
    private boolean m_echoOn = true;
    private MacroDefns m_macros;
    // We'll share conditional stack with TicConditional.
    Object m_cond;
    private Matcher m_matcher;
    private final Queue<Pair<FileLocation, String>> m_matched = new LinkedList<>();
    private String m_str;
}
