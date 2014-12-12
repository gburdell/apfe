//The MIT License
//
//Copyright (c) 2014-      George P. Burdell
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

import gblib.File;
import gblib.Util;
import static gblib.Util.addAllNoDups;
import static gblib.Util.appendIfNotNull;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * `define macro definitions. NOTES from LRM: It shall be an error if any of the
 * remaining formal arguments does not have a default specified. For a macro
 * with arguments, the parentheses are always required in the macro call, even
 * if all the arguments have defaults. It shall be an error to specify more
 * actual arguments than the number of formal arguments.
 *
 * @author kpfalzer
 */
public class MacroDefns {

    /**
     * Delimiter used to separate prefix location from definition. Example:
     * file1.f:45@@DEFN1
     */
    public final static String stDefineDelimitLoc = "@@";

    public MacroDefns() {
    }

    /**
     * Type of macro redefinition check: 1=value difference; 2=file location.
     */
    private static int stRedefinedCheck = 1;

    public static int setRedefinedCheck(int val) {
        return (stRedefinedCheck = val);
    }

    /**
     * Specify whether compilation unit is single source file; or composite set.
     * This setting used for determining scope of macros undef-ness, currently.
     */
    private static boolean stSingleCompilationUnits = false;

    public static boolean setSingleCompilationUnits(boolean val) {
        Helper.info(3, "VPP-SFCU-1", val);
        return (stSingleCompilationUnits = val);
    }

    /**
     * Get list of files which only define macros which are used within used
     * sources and other define only files whose macros are used.
     *
     * @param usedSrcs list of used sources
     * @return list of files which only define macros.
     */
    public List<String> getDefineOnlyFiles(List<String> usedSrcs) {
        return m_macrosUsed.getMacroDefnFiles(usedSrcs);
    }

    private static String squeeze(String s) {
        return s.replaceAll("\\s", "");
    }
    
    private static String trim(String s) {
        return (null != s) ? s.trim() : "";
    }

    /**
     * Check if macro is being redefined in accordance with stRedefinedCheck.
     *
     * @param key macro name.
     * @param loc location of new macro definition.
     * @param defn new definition value (or null).
     * @return true if macro is redefined in accordance with stRedefinedCheck.
     */
    private boolean isRedefined(String key, Location loc, String defn) {
        boolean isRedef = false;
        if (isDefined(key)) {
            Val val = lookup(key);
            //create trimmed versions of definitions for print.
            final String newDefn = trim(defn);
            final String wasDefn = trim(val.m_origDefn);
            //we'll compare a complete squeeze (so 'a+b' is same as 'a + b')
            final boolean eq = squeeze(wasDefn).equals(squeeze(newDefn));
            if (val.m_loc.equals(loc) && !eq) {
                Helper.error("VPP-REDEFN-1", loc.toStringAbsFn(), key, wasDefn, newDefn);
                isRedef = true;
            } else {
                if (1 == stRedefinedCheck) {
                    isRedef = !eq;
                } else {
                    isRedef = !loc.equals(val.m_loc);
                }
                if (isRedef) {
                    Helper.warning("VPP-DUP-1a", loc.toStringAbsFn(), key, newDefn);
                    Helper.warning("VPP-DUP-1b", val.m_loc.toStringAbsFn(), key, wasDefn);
                }
            }
        }
        return isRedef;
    }

    private static final Util.Equals<String> stEquals = new Util.Equals<String>() {

        @Override
        public boolean equals(String a, String b) {
            return a.trim().equals(b.trim());
        }
    };

    private Val lookup(String key) {
        return m_valsByName.get(key);
    }

    public boolean isDefined(String nm) {
        return (m_valsByName.containsKey(nm));
    }

    /**
     * Remove macro definition for 'macnm' for those definition locations within
     * compilation unit named by 'cuFname'.
     *
     * @param macnm macro name to remove.
     * @param cuFname compilation unit filename.
     * @param loc location of `undef directive
     */
    public void undef(String macnm, String cuFname, Location loc) {
        if (!isDefined(macnm)) {
            //VPP-UNDEF-1: %s: '`undef %s' has no effect since '%s' was never defined
            Helper.warning("VPP-UNDEF-1", loc, macnm, macnm);
        } else {
            final gblib.File cuLoc = new gblib.File(cuFname);
            final Val curr = lookup(macnm);
            if (!stSingleCompilationUnits || cuLoc.equals(curr.m_cuLoc)) {
                m_valsByName.remove(macnm);
                Helper.info(2, "VPP-UNDEF-3", loc, macnm, macnm);
            } else {
                Helper.warning("VPP-UNDEF-2", loc, macnm, macnm, cuFname);
            }
        }
    }

    public void add(String nm, String defn, Location loc) {
        final String orig = appendIfNotNull(nm, "=", defn);
        int ix = nm.indexOf(stDefineDelimitLoc); //fname:lnum<delim>
        if (0 < ix) {
            int ix2 = nm.indexOf(":");
            if (0 < ix2) {
                String fn = nm.substring(0, ix2);
                int ln = Integer.parseInt(nm.substring(ix2 + 1, ix));
                loc = new Location(fn, ln, 0);
                nm = nm.substring(ix + stDefineDelimitLoc.length());
            }
            Helper.info(3, "VPP-MAC-1", orig, appendIfNotNull(nm, "=", defn));
        }
        add(nm, null, defn, loc);
    }

    public final static String stNul = "";

    /**
     * Expand macro.
     *
     * @param macnm name of macro.
     * @param args list of actual arguments (or null).
     * @param loc location where macro used.
     * @return expanded macro.
     */
    public String expandMacro(String macnm, List<String> args, Location loc) {
        Val mval = lookup(macnm);
        mval.m_hitCnt++;
        m_macrosUsed.markUsed(macnm, loc);
        List<Parm> parms = mval.m_parms;
        int hasN = (null != args) ? args.size() : 0;
        int expectsN = (null != parms) ? parms.size() : 0;
        if (hasN > expectsN) {
            Helper.error("VPP-ARGN", loc, macnm, hasN, expectsN);
            return null;
        }
        String defn = mval.m_defn;
        int pos = 1;
        String with;
        if (null != args) {
            for (String arg : args) {
                if (arg.isEmpty()) {
                    with = parms.get(pos - 1).getDefault();
                    if (null == with) {
                        with = stNul;    //""
                    }
                } else {
                    with = arg;
                }
                defn = replace(defn, pos, with);
                pos++;
            }
        }
        //from LRM: if more formal than actual, the remaining formal must have 
        //defaults.
        if (null != parms) {
            for (; pos <= expectsN; pos++) {
                with = parms.get(pos - 1).getDefault();
                if (null == with) {
                    Helper.error("VPP-NODFLT", loc, macnm, parms.get(pos - 1).getParmName());
                    return null;
                }
                defn = replace(defn, pos, with);
            }
        }
        return defn;
    }

    private static String replace(String s, int pos, String with) {
        String repl = stDelim[0] + pos + stDelim[1];
        s = s.replace(repl, with);
        return s;
    }

    /**
     * Encode parameter-based macro definition.
     *
     * @param parms list of parameters
     * @param defn definition.
     * @return expanded macro definition.
     */
    private static String encodeDefn(List<Parm> parms, String defn) {
        if (null == parms) {
            return defn;
        }
        /* replace each parameter value w/ </%n%/> where n is
         * parm position, 1-origin.
         */
        //Get parameter names in descending order of length
        List<String> names = new LinkedList<>();
        for (Parm p : parms) {
            names.add(p.getParmName());
        }
        List<String> sorted = new LinkedList<>(names);
        Collections.sort(sorted, new Comparator<String>() {
            @Override
            public int compare(String t, String t1) {
                //return by descending order
                int n = t.length(), n1 = t1.length();
                return (n == n1) ? 0 : ((n < n1) ? 1 : -1);
            }
        });
        for (String p : sorted) {
            int ix = names.indexOf(p) + 1;  //which parm
            String repl = stDelim[0] + ix + stDelim[1];
            defn = replace(defn, p, repl);
        }
        return defn;
    }

    public void add(String nm, List<Parm> parms, final String defn, Location loc) {
        isRedefined(nm, loc, defn);
        String encodedDefn = encodeDefn(parms, defn);
        m_macrosUsed.addDefn(nm, loc);
        final String cuFname = Helper.getTheOne().getFname();
        m_valsByName.put(nm, new Val(parms, encodedDefn, loc, cuFname, defn));
    }

    /**
     * Replace occurrences of 'id' in 'src' with 'repl', iff. 'id' is not part
     * of another id.
     *
     * @param src source string.
     * @param id id to search for (and replace).
     * @param repl replacement string.
     * @return updated string.
     */
    private static String replace(String src, String id, String repl) {
        final String spatt = "(^|\\W)(" + id + ")($|\\W)";
        /*This did not work, so try explicitly
         final String capRepl = "$1" + repl;   //replace 2-nd capture
         String rval = src.replaceAll(spatt, capRepl);
         */
        String rval = src;
        Pattern patt = Pattern.compile(spatt);
        while (true) {
            Matcher m = patt.matcher(rval);
            if (false == m.find()) {
                break;//while
            }
            int start = m.start(2), end = m.end(2);
            StringBuilder ns = new StringBuilder();
            if (0 < start) {
                ns.append(rval.substring(0, start));
            }
            ns.append(repl);
            if (end < rval.length()) {
                ns.append(rval.substring(end));
            }
            rval = ns.toString();
        }
        return rval;
    }
    // {[0],[1]}={prefix,suffix}
    private static final String stDelim[] = new String[]{"</%", "%/>"};
    /**
     * Map of macro defn by macro name.
     */
    private Map<String, Val> m_valsByName = new HashMap<>();
    /**
     * More detailed tracking of macro usage.
     */
    private MacrosUsed m_macrosUsed = new MacrosUsed();

    /**
     * Track where macros are defined and if they are used. From this info, can
     * return files which may have only defined macros. These are used if any of
     * the macros are actually used.
     */
    private static class MacrosUsed {

        /**
         * Add (new) definition.
         *
         * @param macro macro being defined.
         * @param loc location of (new) definition.
         */
        private void addDefn(String macro, Location loc) {
            if (null != loc) {
                File f = new File(loc.getFilename());
                List<File> locs;
                if (!m_filesByMacro.containsKey(macro)) {
                    locs = new LinkedList<>();
                    m_filesByMacro.put(macro, locs);
                } else {
                    locs = m_filesByMacro.get(macro);
                }
                if (!locs.contains(f)) {
                    locs.add(f);
                }
            }
        }

        /**
         * Mark macro usage.
         *
         * @param macro macro being used.
         * @param loc location of usage.
         */
        private void markUsed(String macro, Location loc) {
            if (null != loc) {
                File f = new File(loc.getFilename());
                List<String> l = null;
                if (!m_macrosUsed.containsKey(f)) {
                    l = new LinkedList<>();
                    m_macrosUsed.put(f, l);
                } else {
                    l = m_macrosUsed.get(f);
                }
                if (!l.contains(macro)) {
                    l.add(macro);
                }
            }
        }

        /**
         * Go through usedSrcs and create unique list of used macros. Foreach
         * macro, get list of sources which define macro. Foreach (macro)
         * source, return source if not in usedSrcs
         *
         * @param usedSrcs list of files which are used. This set is the union
         * of source files and included files (known a priori).
         * @return list of source files which define macros used by usedSrcs but
         * are not in usedSrcs.
         */
        private List<String> getMacroDefnFiles(final List<String> usedSrcs) {
            List<String> rval = new LinkedList<>();
            List<String> allUsed = new LinkedList<>(usedSrcs);
            int lastAllUsedSize = 0;
            do {
                lastAllUsedSize = allUsed.size();
                List<String> newUsed = getMacroDefnFilesIter(allUsed);
                addAllNoDups(rval, newUsed);
                allUsed.addAll(newUsed);
            } while (lastAllUsedSize != allUsed.size());
            return rval;
        }

        private List<String> getMacroDefnFilesIter(final List<String> usedSrcs) {
            List<String> rval = new LinkedList<>();
            List<File> usedFiles = new LinkedList<>();
            //Hash/keys of used macros
            Map<String, Object> usedMacros = new HashMap<>();
            //1) get macros used
            for (String fn : usedSrcs) {
                File f = new File(fn);
                usedFiles.add(f);
                if (m_macrosUsed.containsKey(f)) {
                    for (String macro : m_macrosUsed.get(f)) {
                        if (!usedMacros.containsKey(macro)) {
                            usedMacros.put(macro, null);    //value if nil, not used.
                        }
                    }
                }
            }
            //2) get sources which defined macro not in usedFiles
            for (String macro : usedMacros.keySet()) {
                for (File src : m_filesByMacro.get(macro)) {
                    if (!usedFiles.contains(src)) {
                        rval.add(src.getFilename());
                    }
                }
            }
            return rval;
        }
        /**
         * Map of file which defined macro/key.
         */
        private Map<String, List<File>> m_filesByMacro = new HashMap<>();
        /**
         * Map of macros used by File/key.
         */
        private Map<File, List<String>> m_macrosUsed = new HashMap<>();
    }

    private static class Val {

        public Val(String defn) {
            this(null, defn, null, null);
        }

        public Val(List<Parm> parms, String defn, Location loc, String cuFname, String origDefn) {
            m_parms = parms;
            m_defn = defn;
            m_loc = (null != loc) ? loc : Location.stCmdLine;
            m_cuLoc = (null != cuFname) ? new gblib.File(cuFname) : null;
            m_origDefn = origDefn.trim();
        }
        
        public Val(List<Parm> parms, String defn, Location loc, String cuFname) {
            this(parms, defn, loc, cuFname, defn);
        }        

        public boolean hasParms() {
            return (null != m_parms);
        }
        public final List<Parm> m_parms;
        // Encoded definition
        public final String m_defn;
        // Original definition
        public final String m_origDefn;
        public final Location m_loc;
        //compilation unit filename (null if command line)
        public final gblib.File m_cuLoc;
        /**
         * Track how many times macro is referenced.
         */
        private int m_hitCnt = 0;
    }
}
