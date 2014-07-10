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

import apfe.dsl.vlogpp.Location;
import apfe.dsl.vlogpp.Helper;
import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer;
import apfe.runtime.CharBuffer.Marker;
import apfe.runtime.CharSeq;
import apfe.runtime.InputStream;
import apfe.runtime.Memoize;
import apfe.runtime.RestOfLine;
import apfe.runtime.Sequence;
import apfe.runtime.State;
import apfe.runtime.Util;
import java.io.File;

/**
 *
 * @author gburdell
 */
public class TicInclude extends Acceptor {

    private boolean match(char c1, char c2) {
        Sequence s1 = new Sequence(new CharSeq(c1), new FileName(), new CharSeq(c2));
        boolean m = (null != (s1 = match(s1)));
        if (m) {
            m_fname = Util.extractEleAsString(s1, 1);
        }
        return m;
    }

    @Override
    protected boolean accepti() {
        //TicInclude <- "`include" Spacing ('"' FileName '"' / '<' FileName '>')
        Marker start = getStartMark();
        CharSeq c1 = new CharSeq("`include");
        boolean match = matchTrue(new Sequence(c1, new Spacing()));
        Location loc = Location.getCurrent();
        match &= match('"', '"') || match('<', '>');
        //Grab spacing afterwards so we can get next start line
        match &= (new RestOfLine()).acceptTrue();
        if (match) {
            match &= processIncludeFile(loc, start);
        }
        return match;
    }

    private String m_fname;

    private boolean processIncludeFile(Location loc, Marker start) {
        CharBuffer currBuf = Helper.getBuf();
        Marker here = currBuf.mark();
        String currFn = currBuf.getFileName();
        File incl = Helper.getTheOne().getInclFile(loc, m_fname);
        if (null == incl) {
            Helper.error("VPP-INCL-4", loc, m_fname);
            return true;    //gooble up anyway else `include triggers `macroInst
        }
        //Build up stuffing
        StringBuilder sb = new StringBuilder();
        sb.append("`line 1").append(" \"").append(incl.getName())
                .append("\" 1\n");
        boolean ok = true;
        try {
            InputStream fis = new InputStream(incl.getName());
            CharBuffer buf = fis.newCharBuffer();
            sb.append(buf.getBuf());    //tack on file contents
            sb.append("\n`line ").append(start.getLnum())
                    .append(" \"").append(currFn).append("\" 2\n");
            currBuf.replace(start, sb.toString());
            currBuf.reset(start);
            Memoize.reset();
        } catch (Exception ex) {
            ok = false;//Logger.getLogger(TicInclude.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ok;
    }

    @Override
    public Acceptor create() {
        return new TicInclude();
    }
}
