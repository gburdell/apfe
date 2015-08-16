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

import static gblib.Util.downCast;

/**
 *
 * @author gburdell
 */
/**
 * A character buffer.
 */
public class CharBuffer {

    /**
     * Create buffer associated with 'fname'. Buffer size is determined by
     * (1+extra) * buf.size.
     *
     * @param fname filename associated with buffer.
     * @param buf initial buffer contents.
     * @param extra extra size scaling factor.
     */
    public CharBuffer(String fname, CharSequence buf, float extra) {
        reset(fname, 1);
        final int n = buf.length();
        int sz = (int) ((1 + extra) * n);
        assert sz >= n;
        m_buf = new StringBuilder(sz);
        m_buf.append(buf);
        assert n == m_buf.length();
    }

    public CharBuffer(String fname, CharSequence buf) {
        this(fname, new StringBuilder(buf));
    }

    public CharBuffer(String fname, StringBuilder buf) {
        m_fname = fname;
        m_buf = buf;
        m_lnum = 1;
        m_col = m_pos = 0;
    }

    public final void reset(String fname, int lnum) {
        m_fname = fname;
        m_lnum = lnum;
        m_col = 0;
    }

    public StringBuilder getBuf() {
        return m_buf;

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
        return new MarkerImpl();
    }

    public Marker mark(int offset) {
        return new MarkerImpl(offset);
    }

    /**
     * Reset to marker position.
     *
     * @param mark marker position.
     */
    public void reset(Marker mark) {
        MarkerImpl backTo = downCast(mark);
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
        return (n < m_buf.length()) ? m_buf.charAt(n) : EOF;
    }

    public String getFileName() {
        return m_fname;
    }

    public int getLine() {
        return m_lnum;
    }

    public int getCol() {
        return 1 + m_col;
    }

    public String getPos() {
        return getLine() + ":" + getCol();
    }

    /**
     * Return buffer contents from start to current position.
     *
     * @param start start marker.
     * @return buffer contents from start to current position.
     */
    public String substring(final Marker start) {
        MarkerImpl mark = downCast(start);
        assert mark.m_xpos <= m_pos;
        return m_buf.substring(mark.getPos(), m_pos);
    }

    /**
     * Replace buffer contents from start to current position with s.
     *
     * @param start start marker.
     * @param s replace string.
     * @param doReset if true reset to start mark; else, advance past s.
     */
    public void replace(final Marker start, String s, boolean doReset) {
        MarkerImpl mark = downCast(start);
        final int begin = mark.getPos();
        if (null != s) {
            getBuf().replace(begin, m_pos, s);
        } else {
            getBuf().delete(begin, m_pos);
        }
        reset(start);
        if (!doReset) {
            accept(s.length());
        }
    }

    public void replace(final Marker start, String s) {
        replace(start, s, true);
    }

    /**
     * Replace current CharBuffer contents [start,current) with space, while
     * retaining any newline. Clear/invalidate any memoization too.
     *
     * @param start start position.
     */
    public void replace(final Marker start) {
        MarkerImpl mark = downCast(start);
        for (int i = mark.getPos(); i < m_pos; i++) {
            if (m_buf.charAt(i) != NL) {
                m_buf.setCharAt(i, ' ');
            }
        }
    }

    public final static char NUL = 0;
    public final static char EOF = (char) -1;
    public final static char NL = '\n';    //LineNumberReader converts all to this.
    private final StringBuilder m_buf;
    private String m_fname;
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

    public class MarkerImpl extends Marker {

        public MarkerImpl() {
            m_xpos = m_pos;
            m_xlnum = m_lnum;
            m_xcol = m_col;
        }

        public MarkerImpl(int offset) {
            m_xpos = m_pos + offset;
            m_xlnum = m_lnum;
            m_xcol = m_col + offset;
        }

        @Override
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

        @Override
        public String getFileName() {
            return CharBuffer.this.getFileName();
        }

        @Override
        public int getCol() {
            return 1 + m_xcol;
        }
        private final int m_xpos, m_xlnum, m_xcol;
    }
}
