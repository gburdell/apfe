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

import apfe.v2.vlogpp.TicDefine.FormalArg;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author gburdell
 */
public class MacroDefns {

    public static enum EStrictness {

        eAllowRedefn, //false is most strict
        eRedefnSameValue,
        eRedefnSameLocation,
        eRedefnWarning, //show any redefn as warning (except when error)
        eNotUsed    //mark last ordinal
    }

    public static void setStrictness(final boolean val, final EStrictness... flags) {
        for (EStrictness flag : flags) {
            stStrictness.set(flag.ordinal(), val);
        }
    }

    public static void setStrictness(final EStrictness... flags) {
        setStrictness(true, flags);
    }

    public static void clrStrictness(final EStrictness... flags) {
        setStrictness(false, flags);
    }

    public static boolean getStrictness(final EStrictness flag) {
        return stStrictness.get(flag.ordinal());
    }

    public boolean hasDefn(final String macroNm) {
        return m_defns.containsKey(macroNm);
    }
    
    public static class Defn {

        Defn(final FileLocation loc, final String macroNm, final List<FormalArg> formalArgs,
                final String macroText) {
            m_loc = loc;
            m_macroNm = macroNm;
            m_formalArgs = formalArgs;
            m_macroText = macroText;
        }

        Defn(final String macroNm, final List<FormalArg> formalArgs,
                final String macroText) {
            this(null, macroNm, formalArgs, macroText);
        }

        Defn(final String macroNm) {
            this(null, macroNm, null, null);
        }

        // Location where defined.  null if cmdline.
        private final FileLocation m_loc;
        private final String m_macroNm;
        private final List<FormalArg> m_formalArgs;
        private final String m_macroText;
    }

    public void addDefn(final TicDefine defn) throws ParseError {
        addDefn(defn.getDefn());
    }
    
    public void addDefn(final Defn defn) throws ParseError {
        if (m_defns.containsKey(defn.m_macroNm)) {
            redefnDetected(defn);
        }
        m_defns.put(defn.m_macroNm, defn);
    }

    public static final String stCmdLine = "<cmdline>";

    private void redefnDetected(final Defn defn) throws ParseError {
        final Defn prev = m_defns.get(defn.m_macroNm);
        final String prevLoc = getLocation(prev.m_loc),
                currLoc = getLocation(defn.m_loc);
        if (false == getStrictness(EStrictness.eAllowRedefn)) {
            throw new ParseError("VPP-REDEFN-1", currLoc, defn.m_macroNm, prevLoc);
        }
        //OK. We allow redefinition, ...
        final String prevVal = (null != prev.m_macroText) ? squeeze(prev.m_macroText) : "";
        final String val = (null != defn.m_macroText) ? squeeze(defn.m_macroText) : "";
        if (!val.equals(prevVal) && getStrictness(EStrictness.eRedefnSameValue)) {
            throw new ParseError("VPP-REDEFN-2", currLoc, defn.m_macroNm, prevLoc);
        }
        if (!FileLocation.equals(prev.m_loc, defn.m_loc) && getStrictness(EStrictness.eRedefnSameLocation)) {
            throw new ParseError("VPP-REDEFN-3", currLoc, defn.m_macroNm, prevLoc);
        }
        if (getStrictness(EStrictness.eRedefnWarning)) {
            Messages.message('W', "VPP-REDEFN-1", currLoc, defn.m_macroNm, prevLoc);
        }
    }

    private static String getLocation(final FileLocation loc) {
        return (null != loc) ? loc.toString() : stCmdLine;
    }

    private static String squeeze(String s) {
        return s.replaceAll("\\s", "");
    }

    private final Map<String, Defn> m_defns = new HashMap<>();
    private static final BitSet stStrictness = new BitSet(EStrictness.eNotUsed.ordinal());

    static {    //default: allow redefn with warning
        setStrictness(EStrictness.eAllowRedefn, EStrictness.eRedefnWarning);
        clrStrictness(EStrictness.eRedefnSameLocation, EStrictness.eRedefnSameValue);
    }

}
