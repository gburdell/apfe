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

import static v2.apfe.runtime.Acceptor.match;
import static v2.apfe.runtime.CharBuffer.NUL;
import static v2.apfe.runtime.CharBuffer.EOF;
import static v2.apfe.runtime.CharBuffer.NL;

/**
 * Char specific single character or any != EOF.
 *
 * @author gburdell
 */
public class Char extends Acceptor {

    /**
     * Match next character with any != EOF.
     *
     * @param buf lookahead.
     * @return next Char != EOF or null on EOF.
     */
    public static Char match() {
        return match(new Char());
    }

    public static Char match(char c) {
        return match(new Char(c));
    }

    public Char() {
        this(NUL);
    }

    public Char(char c) {
        m_expect = c;
    }

    @Override
    protected boolean accepti() {
        CharBuffer buf = CharBufState.asMe().getBuf();
        m_char = buf.la();
        boolean match;
        if (NUL == m_expect) {
            match = (EOF != m_char);
        } else {
            match = (m_char == m_expect);
        }
        if (match) {
            buf.accept();
        } else {
            ParseError.push(m_char, toString(m_expect));
        }
        return match;
    }

    /**
     * Get String representation of character.
     *
     * @param c character (could be EOF, NL, NUL
     * @return string representation of c.
     */
    public static String toString(char c) {
        String s;
        switch (c) {
            case EOF:
                s = "<EOF>";
                break;
            case NUL:
                s = "<NUL>";
                break;
            case NL:
                s = "<NL>"; // "\\n"
                break;
            case '\t':
                s = "<TAB>"; //"\\t";
                break;
            default:
                s = new String(new char[]{c});
        }
        return s;
    }

    @Override
    public String toString() {
        return getChar(false);
    }

    public String getChar() {
        return getChar(true);
    }
    
    /**
     * Get char with optional escape (if char=='\'').
     * @param withEsc specify true to prefix with escape if '\''.
     * @return optionally escaped char.
     */
    public String getChar(boolean withEsc) {
        char cb[] = (withEsc && (m_char=='\'')) 
                ? new char[]{'\\',m_char}
                : new char[]{m_char};
        return new String(cb);
    }

    private final char m_expect;
    private char m_char;

    @Override
    public Acceptor create() {
        return new Char(m_expect);
    }
}
