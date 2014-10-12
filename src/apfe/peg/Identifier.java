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
package apfe.peg;

import apfe.peg.generate.GenJava;
import apfe.peg.generate.Main;
import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer.Marker;
import apfe.runtime.Memoize;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;

public class Identifier extends Acceptor implements GenJava.IGen {

    public Identifier() {
    }

    @Override
    public GenJava genJava(GenJava j) {
        String s;
        if (Main.stGenMaze) {
            s = getId() + ".OaO"; 
        } else {
            s = "new " + GenJava.getClsNm(getId()) + "()";
        }
        return j.append(s);
    }

    @Override
    protected boolean accepti() {
        // Identifier <- IdentStart IdentCont* Spacing
        IdentStart e1 = new IdentStart();
        Repetition e2 = new Repetition(new IdentCont(), Repetition.ERepeat.eZeroOrMore);
        Sequence seq = new Sequence(e1, e2);
        boolean match = seq.acceptTrue();
        if (match) {
            m_literal = super.toString();
            match &= (new Spacing()).acceptTrue();
        }
        return match;
    }

    @Override
    public String toString() {
        return m_literal;
    }
    
    public String getId() {
        return m_literal;
    }
    
    private String m_literal;

    @Override
    public Acceptor create() {
        return new Identifier();
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
     * Memoize for all instances of Identifier.
     */
    private static Memoize stMemo = new Memoize();
}
