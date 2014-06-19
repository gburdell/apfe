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

/**
 *
 * @author gburdell
 */
public interface ICharClass {

    /**
     * Test if character is in a class of characters (like digits, alpha, ...)
     *
     * @param c character to test.
     * @return true if character is member of class.
     */
    public boolean inClass(char c);

    /**
     * Get expected character set. This method used for error message
     * generation.
     *
     * @return expected character set.
     */
    public String getExpected();

    /**
     * Convenience digit class.
     */
    public final static ICharClass IS_DIGIT = new ICharClass() {
        @Override
        public boolean inClass(char c) {
            return (('0' <= c) && ('9' >= c));
        }

        @Override
        public String getExpected() {
            return "[0-9]";
        }
    };
    /**
     * Convenience alpha (a-z) class.
     */
    public final static ICharClass IS_ALPHA = new ICharClass() {
        @Override
        public boolean inClass(char c) {
            return IS_LOWERCASE.inClass(c) || IS_UPPERCASE.inClass(c);
        }

        @Override
        public String getExpected() {
            return "[a-zA-Z]";
        }
    };
    public final static ICharClass IS_LOWERCASE = new ICharClass() {
        @Override
        public boolean inClass(char c) {
            return (('a' <= c) && ('z' >= c));
        }

        @Override
        public String getExpected() {
            return "[a-z]";
        }
    };
    public final static ICharClass IS_UPPERCASE = new ICharClass() {
        @Override
        public boolean inClass(char c) {
            return (('A' <= c) && ('Z' >= c));
        }

        @Override
        public String getExpected() {
            return "[A-Z]";
        }

    };
    public final static ICharClass IS_ALPHANUM = new ICharClass() {
        @Override
        public boolean inClass(char c) {
            return IS_ALPHA.inClass(c) || IS_DIGIT.inClass(c);
        }

        @Override
        public String getExpected() {
            return "[a-zA-Z0-9]";
        }

    };
    public final static ICharClass IS_ANY = new ICharClass() {
        @Override
        public boolean inClass(char c) {
            return true;
        }

        @Override
        public String getExpected() {
            return "[.]";
        }

    };
}
