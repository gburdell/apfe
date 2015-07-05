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
package v2.apfe.runtime;

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
    //By default, add 20% extra space in buffer.
    public static float stExtra = 0.2f;
    
    /**
     * Create CharBuffer initialized with file contents.
     * @param fname filename to read.
     * @param extra initialize buffer with (1+extra)*sizeof(file).
     * @return populated CharBuffer with extra margin.
     * @throws Exception if file cannot be read or other unrecoverable error.
     */
    public static CharBuffer create(String fname, float extra) throws Exception {
        InputStream ins = new InputStream(fname, extra);
        return new CharBuffer(fname, ins.m_buf);
    }
    
     /**
     * Create CharBuffer initialized with file contents.
     * @param fname filename to read.
     * @return populated CharBuffer with extra margin given by default (stExtra).
     * @throws Exception if file cannot be read or other unrecoverable error.
     */
    public static CharBuffer create(String fname) throws Exception {
        return create(fname, stExtra);
    }
    
    /**
     * Create InputStream from file source.
     *
     * @param fname file name.
     * @throws Exception if file cannot be opened or other unrecoverable error.
     */
    public InputStream(String fname) throws Exception {
        this(fname, stExtra);
    }

    public InputStream(String fname, float extra) throws Exception {
        m_fname = fname;
        init(extra);
    }

    /**
     * Get a character stream bound to this buffer.
     *
     * @return character stream.
     */
    public CharBuffer newCharBuffer() {
        return new CharBuffer(m_fname, m_buf);
    }

    private void init(float overhead) throws Exception {
        Path path = FileSystems.getDefault().getPath(m_fname);
        long lsz = Files.size(path);
        if (lsz > Integer.MAX_VALUE) {
            gblib.Util.abnormalExit(lsz + " > " + Integer.MAX_VALUE);
        }
        int sz = (int) (1 + overhead) * gblib.Util.longToInt(lsz);
        m_buf = new StringBuilder(sz);
        int i;
        try (LineNumberReader rdr = new LineNumberReader(new FileReader(m_fname))) {
            //Need to use read()/singular since read([],..) does not discard \r.
            while (0 <= (i = rdr.read())) {
                m_buf.append(gblib.Util.intToChar(i));
            }
        }
    }
    private final String m_fname;
    private StringBuilder m_buf;
}
