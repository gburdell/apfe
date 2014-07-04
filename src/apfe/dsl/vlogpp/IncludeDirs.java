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

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * A convenience class for manipulating include dirs and include files.
 *
 * @author gburdell
 */
public class IncludeDirs {

    public static IncludeDirs create(String dirNm) {
        IncludeDirs rval = new IncludeDirs();
        rval.add(dirNm);
        return rval;
    }

    /**
     * Add dirNm to search path, only if it is unique.
     *
     * @param dirNm directory to add.
     * @return false if invalid directory.
     */
    public final boolean add(String dirNm) {
        File d = new File(dirNm);
        boolean ok = d.canRead() && d.exists() && d.isDirectory();
        if (ok) {
            final String asAbs;
            try {
                asAbs = d.getCanonicalPath();
            } catch (IOException ex) {
                Main.error("VPP-DIR-1", dirNm);
                return false;
            }
            for (File i : m_inclDirs) {
                try {
                    if (asAbs.equals(i.getCanonicalPath())) {
                        d = null;
                        break;
                    }
                } catch (IOException ex) {
                    Main.error("VPP-DIR-2", i.getName(), "access");
                }
            }
            if (null != d) {
                m_inclDirs.add(d);
            }
        } else {
            Main.error("VPP-DIR-1", dirNm);
        }
        return ok;
    }

    /**
     * Find all occurrences of include file using search path.
     *
     * @param fn include file to find.
     * @return list of all include file candidates (in search path order).
     */
    public List<File> findInclFile(String fn) {
        List<File> rval = new LinkedList<>();
        if (fn.startsWith(File.separator)) {
            File f = new File(fn);
            if (f.canRead()) {
                rval.add(f);
            } else {
                fileReadError(f);
            }
        } else {
            for (File dir : m_inclDirs) {
                File ff = new File(dir, fn);
                if (ff.isFile() && ff.canRead()) {
                    addIfNotExists(rval, ff);
                }
            }
        }
        return rval;
    }

    /**
     * Add file to list iff. not already exists (in canonical form).
     *
     * @param list list to add to.
     * @param item add file iff. not already exists.
     */
    private static void addIfNotExists(List<File> list, File item) {
        final String canon;
        try {
            canon = item.getCanonicalPath();
        } catch (IOException ex) {
            fileReadError(item);
            return;
        }
        for (File i : list) {
            try {
                if (i.getCanonicalPath().equals(canon)) {
                    return;
                }
            } catch (IOException ex) {
                fileReadError(i);
            }
        }
        list.add(item);
    }

    private static void fileReadError(final File f) {
        Main.error("VPP-FILE-1", f.getName(), "read");
    }

    private List<File> m_inclDirs = new LinkedList<>();
}
