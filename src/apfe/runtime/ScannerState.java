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
package apfe.runtime;

/**
 * Encapsulate parser state so more can pass (entire state) as single object.
 *
 * @author gburdell
 */
public class ScannerState extends State {

    public static ScannerState asMe() {
        return Util.downCast(getTheOne());
    }
    
    public static ScannerState create(Scanner tokens) {
        ScannerState ele = new ScannerState(tokens);
        init(ele);
        return asMe();
    }

    private ScannerState(Scanner tokens) {
        m_tokens = tokens;
    }

    public CharBuffer getBuf() {
        return m_buf;
    }

    @Override
    public String getFileName() {
        return getBuf().getFileName();
    }

    @Override
    public Marker getCurrentMark() {
        return getBuf().mark();
    }

    @Override
    public boolean isEOF() {
        return (Token.EOF == la().getCode());
    }

    private Token la() {
        return m_tokens.get(m_pos);
    }
    
    private final Scanner m_tokens;
    /**
     * Current position in m_tokens.
     */
    private int m_pos;

    @Override
    public void reset(Marker mark) {
        m_buf.reset(mark);
    }

    @Override
    public String substring(Marker start) {
        return getBuf().substring(start);
    }
    
}
