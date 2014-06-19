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

import java.io.FileReader;
import java.io.LineNumberReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Source of characters.
 *
 * @author gburdell
 */
public class InputStream {
    /**
     * Create InputStream from file source.
     *
     * @param fname file name.
     * @throws Exception if file cannot be opened or other unrecoverable error.
     */
    public InputStream(String fname) throws Exception {
        m_fname = fname;
        init();
    }
    /**
     * Get a character stream bound to this buffer.
     * TODO: use multi-threading to do parallel parse attempts?
     * @return character stream.
     */
    public CharBuffer newCharBuffer() {
        return new CharBuffer(m_fname, m_buf, m_len);
    }
    private void init() throws Exception {
        Path path = FileSystems.getDefault().getPath(m_fname);
        int sz = Util.longToInt(Files.size(path));
        m_buf = new char[sz];
        int i;
        try (LineNumberReader rdr = new LineNumberReader(new FileReader(m_fname))) {
            while (0 <= (i = rdr.read())) {
                m_buf[m_len++] = Util.intToChar(i);
            }
        }
    }
    private final String m_fname;
    /**
     * The buffer.
     */
    private char m_buf[];
    /**
     * Length of buffer.
     */
    private int m_len;
}
