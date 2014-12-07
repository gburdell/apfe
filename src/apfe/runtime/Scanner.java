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

import java.util.ArrayList;

/**
 *
 * @author gburdell
 */
public abstract class Scanner extends ArrayList<Token> {

    /**
     * Check for EOF while reading file to create tokens. Not to be confused
     * with checking EOF while post-processing tokens.
     *
     * @return true on EOF while reading file and generating tokens.
     */
    public abstract boolean isEOF();

    public abstract Token nextToken();

    public void slurp() {
        Token tok;
        while (!isEOF()) {
            tok = nextToken();
            super.add(tok);
        }
        super.add(Token.create("<EOF>", Token.EOF));
    }

    /**
     * Get the string associated with token code.
     *
     * @param tokCode token code.
     * @return string associated with token code.
     */
    public abstract String getAsString(int tokCode);
}