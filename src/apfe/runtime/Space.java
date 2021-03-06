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

public class Space extends Acceptor {
    public Space() {
    }

    @Override
    protected boolean accepti() {
        CharBuffer buf = CharBufState.asMe().getBuf();
        boolean match;
        char la = buf.la();
        switch (la) {
            case ' ':   //fall through
            case '\t':  //fall through
            case CharBuffer.NL: //fall through
                match = true;
                m_ch = la;
                break;
            default:
                match = false;
        }
        if (match) {
            buf.accept();
        } else {
            ParseError.push(la, "[ \\t\\n]");
        }
        return match;
    }

    private char m_ch;
    
    @Override
    public String toString() {
        return new String(new char[]{m_ch});
    }
    
    @Override
    public Acceptor create() {
        return new Space();
    }

    @Override
    protected void memoize(Marker mark, Marker endMark) {
        stMemo.add(mark, this, endMark);
    }

    @Override
    protected Memoize.Data hasMemoized(Marker mark) {
        return stMemo.memoized(mark);
    }
    
    /**
     * Memoize for all instances of Space.
     */
    private static Memoize stMemo = new Memoize();
}
