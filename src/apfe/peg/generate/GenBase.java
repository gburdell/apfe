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
public class GenBase extends Template {

    @Override
    public String toString() {
        return m_sb.toString();
    }
    
    public <T extends GenBase> T append(String s) {
        m_sb.append(s);
        return downCast(this);
    }

    public <T extends GenBase> T append(char c) {
        m_sb.append(c);
        return downCast(this);
    }

    public <T extends GenBase> T template(String tmpl, Object... args) {
        m_sb.append(replace(tmpl, args));
        return downCast(this);
    }

    protected StringBuilder m_sb = new StringBuilder();

}
