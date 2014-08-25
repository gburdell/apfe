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
package apfe.maze.runtime;

/**
 *
 * @author gburdell
 */
public class Token {
    public static final int EOF = -1;
    
    public Token(String fname, int lnum, int col, String text, int code) {
        m_fname = fname;
        m_lnum = lnum;
        m_col = col;
        m_text = text;
        m_code = code;
    }

    public boolean isEOF() {
        return (EOF == getCode());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getLocation());
        sb.append(':').append(getCode()).append(':').append(getText());
        return sb.toString();
    }

    public String getLocation() {
        StringBuilder sb = new StringBuilder();
        if (stLocationHasFileName) {
            sb.append(getFileName()).append(':');
        }
        sb.append(getLineNum()).append(':').append(getColNum());
        return sb.toString();
    }

    public int getCode() {
        return m_code;
    }

    public String getFileName() {
        return m_fname;
    }

    public int getLineNum() {
        return m_lnum;
    }

    public int getColNum() {
        return m_col;
    }

    public String getText() {
        return m_text;
    }

    public static boolean stLocationHasFileName = true;

    private final String m_fname;
    private final int m_lnum, m_col;
    private final String m_text;
    private final int m_code;
}
