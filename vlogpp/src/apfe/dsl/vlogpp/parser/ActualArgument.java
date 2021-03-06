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
package apfe.dsl.vlogpp.parser;

import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer;
import static apfe.runtime.CharBuffer.EOF;
import apfe.runtime.Memoize;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Repetition;
import apfe.runtime.CharBufState;
import apfe.runtime.Marker;
import java.util.Stack;

/**
 *
 * @author gburdell
 */
public class ActualArgument extends Acceptor {

    public ActualArgument() {
    }

    /*
     ActualArgument <- STRING
     / '(' !')' ActualArgument ')'
     / '{' !'}' ActualArgument '}'
     / '[' !']' ActualArgument ']'
     / (Space / Comment)+ ActualArgument
     / (NotChar4 . ActualArgument)?          
     */
    private static final String stOpen = "({[";
    private static final String stClose = ")}]";
    
    @Override
    protected boolean accepti() {
        CharBuffer cbuf = CharBufState.asMe().getBuf();
        Acceptor matcher;
        boolean ok = true;
        Stack<Character> matchClose = new Stack<>();
        for (boolean stay = true; stay; /*nil*/) {
            final char c = cbuf.la();
            if (EOF == c) {
                stay = false;
            } else if ('"' == c) {
                matcher = new VString();
                stay &= matchTrue(matcher);
                ok &= stay; //could be bad string
            } else {
                int pos = stOpen.indexOf(c);
                if (0 <= pos) {
                    matchClose.push(stClose.charAt(pos));
                    cbuf.accept();
                } else {
                    PrioritizedChoice pc2 = new PrioritizedChoice(
                            new Space(), new Comment());
                    matcher = new Repetition(pc2, Repetition.ERepeat.eOneOrMore);
                    if (!matchTrue(matcher)) {
                        //not a comment or space, so see if we match close
                        if (!matchClose.empty() && (c == matchClose.peek())) {
                            matchClose.pop();
                            cbuf.accept();
                        } else if ((')' == c) || (matchClose.empty() && (',' == c))) {
                            stay = false;   //normal break on follow-set
                        } else {
                            cbuf.accept();
                        }
                    }
                }
            }
        }
        if (ok) {
            m_str = super.toString();
        }
        ok &= matchClose.isEmpty();  //do string above just for debug
        return ok;
    }

    private String m_str;

    @Override
    public String toString() {
        return m_str;
    }

    @Override
    public Acceptor create() {
        return new ActualArgument();
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
     * Memoize for all instances of ActualArgument.
     */
    private static Memoize stMemo = new Memoize();
}
