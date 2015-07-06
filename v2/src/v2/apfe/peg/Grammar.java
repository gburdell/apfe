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
package v2.apfe.peg;

import v2.apfe.runtime.EndOfFile;
import v2.apfe.runtime.Acceptor;
import v2.apfe.runtime.Repetition;
import v2.apfe.runtime.Sequence;
import v2.apfe.runtime.Util;
import java.util.List;

public class Grammar extends Acceptor {

    public static boolean matchTrue() {
        return matchTrue(new Grammar());
    }

    public Grammar() {
    }

    public List<Definition> getDefns() {
        return m_defns;
    }

    @Override
    protected boolean accepti() {
        //Grammar <- Spacing Definition+ EndOfFile
        Repetition r1 = new Repetition(new Definition(), Repetition.ERepeat.eOneOrMore);
        Sequence s1 = new Sequence(new Spacing(), r1, new EndOfFile());
        boolean match = (null != (s1 = match(s1)));
        if (match) {
            r1 = Util.extractEle(s1, 1);
            m_defns = Util.toList(r1.getAccepted());
        }
        return match;
    }

    private List<Definition> m_defns;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Util.toString(sb, m_defns).append('\n');
        return sb.toString();
    }

    @Override
    public Acceptor create() {
        return new Grammar();
    }

}
