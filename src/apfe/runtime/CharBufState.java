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
 * Encapsulate parser state so more can pass (entire state) as single object.
 *
 * @author gburdell
 */
public class CharBufState extends State {

    public static CharBufState asMe() {
        return downCast(getTheOne());
    }
    
    public static CharBufState create(CharBuffer buf) {
        CharBufState ele = new CharBufState(buf);
        init(ele);
        return asMe();
    }

    private CharBufState(CharBuffer buf) {
        m_buf = buf;
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
        return (CharBuffer.EOF == peek());
    }

    public char peek() {
        return peek(0);
    }

    public char peek(int i) {
        return la(i);
    }

    public char la(int i) {
        return getBuf().la(i);
    }

    private final CharBuffer m_buf;

    @Override
    public void reset(Marker mark) {
        m_buf.reset(mark);
    }

    @Override
    public String substring(Marker start) {
        return getBuf().substring(start);
    }

}
