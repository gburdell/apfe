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
package apfe.leftrecursive;

import apfe.runtime.LeftRecursiveAcceptor;
import apfe.runtime.Acceptor;
import apfe.runtime.CharClass;
import apfe.runtime.Sequence;

public class Fact extends LeftRecursiveAcceptor {

    public Fact() {
    }

    private Fact(boolean isDRR) {
        super(isDRR);
    }

    @Override
    protected int getNonRecursiveChoiceIx() {
        return 2;
    }

    @Override
    public Acceptor create() {
        return new Fact();
    }

    @Override
    public Acceptor getChoice(int ix) {
        Acceptor a = null;
        switch (ix) {
            case 0:
                a = new Sequence(this,//new Fact(),
                        new CharClass(CharClass.matchOneOf("*")),
                        new Fact(true));//for definite right recursion
                break;
            case 1:
                a = new Sequence(this,//new Fact(),
                        new CharClass(CharClass.matchOneOf("/")),
                        new Fact(true));//for definite right recursion
                break;
            case 2:
                a = new Prim();
                break;
        }
        //Keep track so if we growSeed()
        m_lastIx = ix;
        m_lastChoice = a;
        return a;
    }

    //@Override
    public Acceptor getChoiceXX(int ix) {
        Acceptor a = null;
        switch (ix) {
            case 0:
                a = new Sequence(this,//new Fact(),
                        new CharClass(CharClass.matchOneOf("*")),
                        new Prim());
                break;
            case 1:
                a = new Sequence(this,//new Fact(),
                        new CharClass(CharClass.matchOneOf("/")),
                        new Prim());
                break;
            case 2:
                a = new Prim();
                break;
        }
        //Keep track so if we growSeed()
        m_lastIx = ix;
        m_lastChoice = a;
        return a;
    }

    @Override
    public String getNonTermID() {
        return getClass().getSimpleName();
    }
}
