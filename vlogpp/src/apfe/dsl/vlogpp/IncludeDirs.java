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
import java.nio.file.FileSystems;
import static java.nio.file.Files.isSameFile;
import java.nio.file.Path;
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
            final Path asPath;
            asPath = d.toPath();
            for (File i : m_inclDirs) {
                try {
                    if (isSameFile(asPath, i.toPath())) {
                        d = null;
                        break;
                    }
                } catch (IOException unused) {
                    Helper.error("VPP-DIR-2", i.getName(), "access");
                }
            }
            if (null != d) {
                m_inclDirs.add(d);
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
            final List<File> inclDirs = new LinkedList<>(m_inclDirs);
            if (null != stCurrentDir) {
                addIfNotExists(inclDirs, stCurrentDir.toFile(), 0);
            }
            for (File dir : inclDirs) {
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
     * @param ix index to add element (-1 for end).
     */
    private static void addIfNotExists(List<File> list, File item, int ix) {
        if (!item.canRead()) {
            fileReadError(item);
            return;
        }
        final Path asPath = item.toPath();
        for (File i : list) {
            try {
                if (isSameFile(asPath, i.toPath())) {
                    return;
                }
            } catch (IOException unused) {
                fileReadError(i);
            }
        }
        if (0 > ix) {
            list.add(item);
        } else {
            list.add(ix, item);
        }
    }
    
    private static void addIfNotExists(List<File> list, File item) {
        addIfNotExists(list, item, -1);
    }
    
    private static void fileReadError(final File f) {
        Helper.error("VPP-FILE-1", f.getName(), "read");
    }
    
    private final List<File> m_inclDirs = new LinkedList<>();
    
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
