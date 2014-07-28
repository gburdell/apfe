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

import apfe.runtime.Util;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class Template {

    public static String replace(final String tmpl, Object... args) {
        String r = tmpl;
        int i = 1;
        for (Object s : args) {
            String repl = "@" + i++ + "@";
            r = r.replace(repl, s.toString());
        }
        return r;
    }

    /**
     * Replace metachars in tmpl with specified values.
     *
     * @param tmpl template string with metachars.
     * @param args even-number of elements: spec1, repl1, spec2, repl2, ...
     * @return tmpl with each spec1 replaced by repl1, each spec2 replaced by
     * repl2, etc.
     */
    public static String replaceSpecd(final String tmpl, Object... args) {
        final int n = args.length;
        assert (0 == n % 2);
        List<Object> argsl = Arrays.asList(args);
        String r = tmpl;
        for (int i = 0; i < n; i += 2) {
            String repl = argsl.get(i).toString(), with = argsl.get(i + 1).toString();
            r = r.replace(repl, with);
        }
        return r;
    }

    /**
     * Return substring between "beg" and "end" markers.
     *
     * @param tmpl template string to substring.
     * @param beg begin marker.
     * @param end end marker (could be same name as beg: but expect different
     * location).
     * @return substring between beg and end.
     */
    public static String substring(final String tmpl, final String beg, final String end) {
        int begIx = tmpl.indexOf(beg) + beg.length(), endIx = tmpl.lastIndexOf(end);
        return tmpl.substring(begIx, endIx);
    }

    /**
     * Delete substrings delimited by marks.
     * @param ss string containing marks.
     * @param marks begin/end marks.
     * @return remainder of ss after marked substrings are removed.
     */
    public static String delete(final String ss, String... marks) {
        StringBuilder rval = new StringBuilder(ss);
        int beg, end;
        for (String m : marks) {
            beg = rval.indexOf(m);
            end = rval.lastIndexOf(m) + m.length();
            if (0 <= beg) {
                rval.delete(beg, end);
            }
        }
        return rval.toString();
    }
    
    /**
     * Either remove substring between marks or null out marks.
     * @param keep if true: null marks; else remove substring between marks.
     * @param tmpl template/string to modify.
     * @param marks unique marks which appear in pairs in tmpl.
     * @return modified string.
     */
    public static String removeOrKeep(boolean keep, final String tmpl, String... marks) {
        String rval = tmpl;
        if (keep) {
            for (String m : marks) {
                rval = replaceSpecd(rval, m, "");
            }
        } else {
            rval = delete(rval, marks);
        }
        return rval;
    }
}
