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
package apfe.slf;

import apfe.runtime.Acceptor;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Sequence;
import static apfe.runtime.Util.extractEle;

public class Cell extends Acceptor {

    public Cell() {
    }

    @Override
    protected boolean accepti() {
        //Cell <- K_CELL LPAREN (IDENT / STRING) RPAREN ValueSet
        PrioritizedChoice pc1 = new PrioritizedChoice(new PrioritizedChoice.Choices() {
            @Override
            public Acceptor getChoice(int ix) {
                Acceptor a = null;
                switch (ix) {
                    case 0:
                        a = new Ident();
                        break;
                    case 1:
                        a = new SlfString();
                        break;
                }
                return a;
            }
        });
        Sequence s1 = new Sequence(new Operator(Operator.EOp.K_CELL), 
                new Operator(Operator.EOp.LPAREN), pc1, new Operator(Operator.EOp.RPAREN),
                new ValueSet());
        boolean match = (null != (s1 = match(s1)));
        if (match) {
            pc1 = extractEle(s1, 2);
        }
        return match;
    }

    @Override
    public Acceptor create() {
        return new Cell();
    }
    
 }
