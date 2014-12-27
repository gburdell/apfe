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
package apfe.vlogpp2;

import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer;
import apfe.runtime.PrioritizedChoice;

/**
 *
 * @author gburdell
 */
public class TicReserved extends AcceptorWithLocation {

    public TicReserved(CharBuffer.MarkerImpl loc) {
        super(loc);
    }

    /**
     * Parse `_reserved_ statement.
     * It is assumed the `_reserved_ has been detected (but not accepted) upon
     * entry.  Thus, if we have any issue here, we will mark as parse error.
     * @return true on success; else false.
     */
    @Override
    protected boolean accepti() {
        /*
         * TicReserved <- ResetAll
         / Undef
         / UndefineAll
         / TimeScale
         / DefaultNetType
         / CellDefine
         / Pragma
         / Line
         / FileExpand
         / LineExpand
         / BeginKeywords
         / EndKeywords
         */
        m_contents = new PrioritizedChoice(new PrioritizedChoice.Choices() {
            @Override
            public Acceptor getChoice(int ix) {
                Acceptor a = null;
                switch (ix) {
                    case 0:
                        a = new ResetAll();
                        break;
                    case 1:
                        a = new Undef();
                        break;
                    case 2:
                        a = new UndefineAll();
                        break;
                    case 3:
                        a = new TimeScale();
                        break;
                    case 4:
                        a = new DefaultNetType();
                        break;
                    case 5:
                        a = new CellDefine();
                        break;
                    case 6:
                        a = new Pragma();
                        break;
                    case 7:
                        a = new Line();
                        break;
                    case 8:
                        a = new FileExpand();
                        break;
                    case 9:
                        a = new LineExpand();
                        break;
                    case 10:
                        a = new BeginKeywords();
                        break;
                    case 11:
                        a = new EndKeywords();
                        break;
                }
                return a;
            }
        });
        boolean match = (null != (m_contents = match(m_contents)));
        if (match) {
            m_text = super.toString();
        } else {
            setParseError();
        }
        return match;
    }
    private PrioritizedChoice m_contents;
    private String m_text;

    @Override
    public String toString() {
        return m_text;
    }

    @Override
    public Acceptor create() {
        return new TicReserved();
    }
    
    private TicReserved() {
        super(null);
    }
}
