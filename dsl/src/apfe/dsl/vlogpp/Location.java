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

import apfe.runtime.CharBuffer;
import apfe.runtime.CharBufState;
import apfe.runtime.Util;

/**
 * Detailed file location.
 *
 * @author gburdell
 */
public class Location {

    /**
     * Create location object.
     *
     * @param fname filename.
     * @param lnum line number (1-origin).
     * @param col column number (1-origin).
     */
    public Location(String fname, int lnum, int col) {
        m_fname = fname;
        m_lnum = lnum;
        m_col = col;
    }

    /**
     * Get current location.
     *
     * @return current location.
     */
    public static Location getCurrent() {
        CharBuffer cbuf = CharBufState.asMe().getBuf();
        return new Location(cbuf.getFileName(), cbuf.getLine(), cbuf.getCol());
    }

    @Override
    public String toString() {
        return m_fname + ":" + m_lnum + ":" + m_col;
    }

    @Override
    public boolean equals(Object obj) {
        Location other = Util.downCast(obj);
        boolean eq = m_fname.equals(other.m_fname) && (m_lnum == other.m_lnum)
                && (m_col == other.m_col);
        return eq;
    }
    
    private final String m_fname;
    private final int m_lnum, m_col;
    
    public static final Location stCmdLine = new Location("<cmdline>", 0, 0);
}
