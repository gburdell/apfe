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
import apfe.runtime.CharBuffer.MarkerImpl;

/**
 *
 * @author gburdell
 */
public abstract class AcceptorWithLocation extends Acceptor {
    protected AcceptorWithLocation(final MarkerImpl loc) {
        m_currLoc = loc;
    }
    /*package*/ Location getLocation() {
        return Parser.getLocation(m_currLoc);
    }
    /*package*/ boolean hasError() {
        return (null != m_errMsgCode);
    }
    protected void setError(final String msgCode, final Object... args) {
        m_errMsgCode = msgCode;
        m_args = args;
    }
    private final MarkerImpl m_currLoc;
    private String m_errMsgCode;
    private Object m_args[];
}
