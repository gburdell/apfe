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

import gblib.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
            if (!m_usedByInclDir.containsKey(d)) {
                m_usedByInclDir.put(d, false);
            }
        } else {
            Helper.error("VPP-DIR-1", dirNm);
        }
        return ok;
    }

    /**
     * Find all occurrences of include file using search path.
     *
     * @param fn include file to find.
     * @return list of all include file candidates (in search path order).
     */
    @SuppressWarnings("empty-statement")
    public List<File> findInclFile(String fn) {
        Set<File> rval = new LinkedHashSet<>();
        if (fn.startsWith(File.separator)) {
            File f = new File(fn);
            if (f.canRead()) {
                rval.add(f);
            } else {
                fileReadError(f);
            }
        } else {
            final List<File> inclDirs = new LinkedList<>(m_usedByInclDir.keySet());
            File f;
            //Check if found at current directory of file we're processing.
            if (null != stCurrentDir) {
                f = new File(stCurrentDir, fn);
                if (fileIsOK(f) && !rval.contains(f)) {
                    rval.add(f);
                }
            }
            for (File dir : inclDirs) {
                File ff = new File(dir, fn);
                if (fileIsOK(ff) && !rval.contains(ff)) {
                    rval.add(ff);
                    m_usedByInclDir.put(dir, Boolean.TRUE); //mark used
                }
            }
        }
        List<File> rvals = Collections.unmodifiableList(new LinkedList(rval));
        if (!rvals.isEmpty()) {
            try {
                //we'll just take the first one.
                m_usedInclFiles.add(rvals.get(0).getCanonicalPath());
            } catch (IOException ex) {
                ;//do nothing
            }
        }
        return rvals;
    }

    /**
     * Get included files (in order they were referenced).
     * @return included files.
     */
    public List<String> getIncludedFiles() {
        return new LinkedList<>(m_usedInclFiles);
    }
    
    private static boolean fileIsOK(File ff) {
        return ff.isFile() && ff.canRead();
    }
    
    private static void fileReadError(final File f) {
        Helper.error("VPP-FILE-1", f.getName(), "read");
    }
    
    /**
     * Map of used by include directory.
     * The backing list maintains the order paths are added.
     * TODO: make sure this ordering policy is true.
     */
    private final Map<File,Boolean> m_usedByInclDir = new LinkedHashMap<>();
    
    /**
     * Set of used include files.
     * Keys are in order added.
     */
    private final Set<String> m_usedInclFiles = new LinkedHashSet<>();
    
    /**
     * Set current file directory.
     * This is used to handle `include of files which are local to the
     * directory of the file being processed.
     * @param fn current file being processed.
     */
    public static void setCurrentDir(String fn) {
        assert null == stCurrentDir;
        stCurrentDir = FileSystems.getDefault().getPath(fn).getParent();
    }
    
    public static void resetCurrentDir() {
        stCurrentDir = null;
    }

    //The current directory of file being parsed.
    private static Path stCurrentDir;
}
