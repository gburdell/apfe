/*
 * The MIT License
 *
 * Copyright 2013 gburdell.
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
package apfe.runtime;

import java.util.Arrays;

/**
 *
 * @author gburdell
 */
/**
 * A character buffer.
 */
public class CharBuffer {

    public CharBuffer(String fname, final char buf[], int len) {
        m_fname = fname;
        m_buf = buf;
        m_len = len;
        m_lnum = 1;
        m_col = m_pos = 0;
    }

    /**
     * Look ahead at next character. Position is <b>not</b> advanced.
     *
     * @return next character or EOF.
     */
    public char la() {
        return la(0);
    }

    /**
     * Look ahead at <i>i-th</i> character.
     *
     * @param i i-th character.
     * @return i-th character or EOF.
     */
    public char la(int i) {
        return laCheck(i);
    }

    /**
     * Accept current character and advance position. Column position and line
     * number advanced (as necessary).
     *
     * @return character at new current position.
     */
    public char accept() {
        char c = la();
        if (NL == c) {
            m_lnum++;
            m_col = -1; //advance to 0 in next stmt
        }
        if (EOF != c) {
            m_col++;
            m_pos++;
        }
        return c;
    }

    /**
     * Accept next n characters and advance position (by n).
     *
     * @param n number of characters to accept.
     * @return character at new current position or la(0) if n < 1.
     */
    public char accept(int n) {
        char c = la();
        while (0 < n--) {
            c = accept();
        }
        return c;
    }

    /**
     * Get marker to current position.
     *
     * @return marker to current position.
     */
    public Marker mark() {
        return new Marker();
    }

    /**
     * Reset to marker position.
     *
     * @param backTo marker position.
     */
    public void reset(Marker backTo) {
        m_pos = backTo.m_xpos;
        m_lnum = backTo.m_xlnum;
        m_col = backTo.m_xcol;
    }

    /**
     * Look ahead and check for EOF.
     *
     * @param la look ahead distance.
     * @return look ahead or EOF.
     */
    private char laCheck(int la) {
        int n = m_pos + la;
        assert (0 <= n);
        return (n < m_len) ? m_buf[n] : EOF;
    }

    public String getFileName() {
        return m_fname;
    }

    public String getPos() {
        return m_lnum + ":" + (m_col + 1);
    }

    /**
     * Return buffer contents from start to current position.
     * @param start start marker.
     * @return buffer contents from start to current position.
     */
    public String substring(final Marker start) {
        assert start.m_xpos <= m_pos;
        return new String(Arrays.copyOfRange(m_buf, start.m_xpos, m_pos));
    }

    public final static char NUL = 0;
    public final static char EOF = (char) -1;
    public final static char NL = '\n';    //LineNumberReader converts all to this.
    private final char m_buf[];
    private final int m_len;
    private final String m_fname;
    /**
     * Current position in buffer.
     */
    private int m_pos;
    /**
     * Current line number.
     */
    private int m_lnum;
    /**
     * Current position within current line.
     */
    private int m_col;

    public class Marker {

        public Marker() {
            m_xpos = m_pos;
            m_xlnum = m_lnum;
            m_xcol = m_col;
        }

        @Override
        public boolean equals(Object m) {
            return (m_xpos == ((Marker) m).m_xpos);
        }

        @Override
        public int hashCode() {
            return m_xpos;
        }

        public int getPos() {
            return m_xpos;
        }

        @Override
        public String toString() {
            return getLnum() + ":" + getCol();
        }

        public int getLnum() {
            return m_xlnum;
        }

        public int getCol() {
            return 1 + m_xcol;
        }
        private final int m_xpos, m_xlnum, m_xcol;
    }
}
