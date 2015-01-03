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
import apfe.runtime.CharSeq;
import apfe.runtime.Sequence;
import apfe.runtime.Util;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class TicDefine extends AcceptorWithLocation {
    /*package*/ TicDefine(final MarkerImpl currLoc) {
        super(currLoc);
    }

    /**
     * Parse `define statement. It is assumed the `define has been detected (but
     * not accepted) upon entry. Thus, if we have any issue here, we will mark
     * as parse error.
     *
     * @return true on success; else false.
     */
    @Override
    protected boolean accepti() {
        //TicDefine <- "`define" Spacing TextMacroName MacroText
        Sequence s1 = new Sequence(new CharSeq("`define"), new Spacing(),
                new TextMacroName(), new MacroText());
        boolean match = (null != (s1 = match(s1)));
        if (!match) {
            setParseError();
        } else if (match && Helper.getTheOne().getConditionalAllow()) {
            TextMacroName mname = Util.extractEle(s1, 2);
            MacroText mtext = Util.extractEle(s1, 3);
            if (mtext.toString().isEmpty()) {
                mtext = null;
            }
            addDefn(mname, mtext, getLocation());
        }
        Helper.getTheOne().replace(super.getStartMark());
        return match;
    }

    private void addDefn(TextMacroName mname, MacroText mtext, Location loc) {
        Helper mn = Helper.getTheOne();
        String macnm = mname.getId();
        String text = (null != mtext) ? mtext.toString() : null;
        //System.err.println("DEBUG: "+macnm+" defined at "+loc.toStringAbsFn());
        List<Parm> parms = Parm.createList(mname.getFormalArgs());
        mn.getMacroDefns().add(macnm, parms, text, loc);
    }

    @Override
    public Acceptor create() {
        return new TicDefine();
    }

    private TicDefine() {
        super(null);
    }
}
