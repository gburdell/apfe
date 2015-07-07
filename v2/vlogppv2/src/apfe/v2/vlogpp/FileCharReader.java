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

import gblib.File;
import gblib.Util;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author gburdell
 */
public class FileCharReader {
    
    public static final int EOF = -1;
    public static final int NL = '\n';
    
    public FileCharReader(final String fname) throws FileNotFoundException {
        m_file = new File(fname);
        m_ifs = new BufferedReader(new FileReader(m_file), stSize);
        nextLine();
    }

    /**
     * Lookahead within current line.
     *
     * @param n lookahead distance from current position.
     * @return lookahead character, NL, or EOF.
     * line.
     */
    public int la(int n) {
        assert 0 <= n;
        int c = EOF;
        if (! isEOF()) {
            int pos = n + m_pos;
            if (pos < m_buf.length()) {
                c = m_buf.charAt(pos);
            } else {
                assert 0 == n;
                if (nextLine()) {
                    c = m_buf.charAt(n);
                }
            }
        }
        return c;
    }
    
    /**
     * Get next character in current line or EOF;
     * @return 
     */
    public int next() {
        final int c = la(0);
        if (!isEOF()) {
            m_pos++;
        }
        return c;
    }
    
    private boolean nextLine() {
        if (!m_eof) {
            try {
                String line = m_ifs.readLine();
                if (null != line) {
                    m_buf.setLength(0);
                    m_buf.append(line).append(NL);
                    m_lnum++;
                    m_col = 0;
                    m_pos = 0;
                } else {
                    m_ifs.close();
                    m_eof = true;
                }
            } catch (IOException ex) {
                Util.abnormalExit(ex);
            }
        }
        return !isEOF();
    }
    
    public boolean isEOF() {
        return m_eof;
    }
    
    private int m_lnum = 0;
    private int m_col = 0;
    private final File m_file;
    private final BufferedReader m_ifs;
    private int m_pos = 0;
    private StringBuilder m_buf = new StringBuilder(256);
    private boolean m_eof = false;
    
    private static final int stSize = 1 << 20;
}
