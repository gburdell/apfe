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

    public MacroDefns() {
    }

    public void checkDupl(String key, Location loc) {
        if (isDefined(key)) {
            Val val = lookup(key);
            Main.warning("VPP-DUP-1a", loc.toString(), key);
            Main.warning("VPP-DUP-1b", val.m_loc.toString(), key);
        }
    }

    private Val lookup(String key) {
        return m_valsByName.get(key);
    }

    public boolean isDefined(String nm) {
        return (m_valsByName.containsKey(nm));
    }

    private boolean hasParams(String key) {
        return isDefined(key) && m_valsByName.get(key).hasParms();
    }

    public void add(String nm, String defn, Location loc) {
        add(nm, null, defn, loc);
    }
    public final static String stNul = "";

    /**
     * Expand macro.
     *
     * @param macnm name of macro.
     * @param args list of actual arguments (or null)
     * @return expanded macro.
     */
    public String expandMacro(String macnm, List<String> args, Location loc) {
        List<Parm> parms = m_valsByName.get(macnm).m_parms;
        int hasN = (null != args) ? args.size() : 0;
        int expectsN = (null != parms) ? parms.size() : 0;
        if (hasN > expectsN) {
            Main.error("VPP-ARGN", loc, hasN, expectsN);
            return null;
        }
        String defn = m_valsByName.get(macnm).m_defn;
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
                    Main.error("VPP-NODFLT", loc, macnm, parms.get(pos - 1).getParmName());
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
     * Get non-parameterized macro value.
     *
     * @param nm macro name.
     * @return non-parameterized macro value.
     */
    private String getVal(String nm) {
        Val val = m_valsByName.get(nm);
        assert (null == val.m_parms);
        return val.m_defn;
    }

    public void add(String nm, List<Parm> parms, String defn, Location loc) {
        checkDupl(nm, loc);
        if (null == parms) {
            m_valsByName.put(nm, new Val(null, defn, loc));
        } else {
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
            m_valsByName.put(nm, new Val(parms, defn, loc));
        }
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
    private Map<String, Val> m_valsByName = new HashMap<>();

    private static class Val {

        public Val(String defn) {
            this(null, defn, null);
        }

        public Val(List<Parm> parms, String defn, Location loc) {
            m_parms = parms;
            m_defn = defn;
            m_loc = (null != loc) ? loc : Location.stCmdLine;
        }

        public boolean hasParms() {
            return (null != m_parms);
        }
        public final List<Parm> m_parms;
        public final String m_defn;
        public final Location m_loc;
    }
}
