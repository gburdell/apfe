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
package apfe.peg.generate;
import static apfe.runtime.Util.downCast;

/**
 *
 * @author gburdell
 */
public abstract class GenBase extends Template {
    public static String toCamelCase(String s) {
        StringBuilder sb = new StringBuilder(s);
        int i;
        //remove leading _
        while (0 == sb.indexOf("_")) {
            sb.deleteCharAt(0);
        }
        //remove trailing _
        for (i = sb.length() - 1; '_' == sb.charAt(i); i = sb.length() - 1) {
            sb.delete(i, i+1);
        }
        //remove consecutive _
        while (0 <= (i = sb.indexOf("__"))) {
            sb.delete(i, i+1);
        }
        assert (0 < sb.length());
        //to camel case
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        while (0 <= (i = sb.indexOf("_"))) {
            sb.deleteCharAt(i);
            sb.setCharAt(i, Character.toUpperCase(sb.charAt(i)));
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return m_sb.toString();
    }
    
    public GenBase append(String s) {
        m_sb.append(s);
        return this;
    }

    public GenBase append(char c) {
        m_sb.append(c);
        return this;
    }

    public GenBase template(String tmpl, Object... args) {
        m_sb.append(replace(tmpl, args));
        return this;
    }

    protected StringBuilder m_sb = new StringBuilder();

    public static final boolean stGenClsAsCamelCase = Main.getPropertyAsBoolean("genClsAsCamelCase");
}
