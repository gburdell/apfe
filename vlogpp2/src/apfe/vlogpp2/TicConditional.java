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
import apfe.runtime.PrioritizedChoice;
import java.util.Stack;

/**
 *
 * @author gburdell
 */
public class TicConditional extends AcceptorWithLocation {

    public TicConditional(MarkerImpl loc) {
        super(loc);
    }

    private static enum EState {

        eUnconditional,
        eIfdef, eIfndef,
        eElseif, eElse,
        eEndif
    }

    private static final Stack<EState> stStateStk;

    static {
        stStateStk = new Stack<>();
        stStateStk.push(EState.eUnconditional);
    }

    /**
     * Advance next state (with error detection).
     *
     * @param nstate next state.
     * @param tok input symbol (determined next state).
     * @return true if advance with no error; else false/error.
     */
    private boolean nextState(final EState nstate, final String tok) {
        /*
         TicConditional <- 
         ("`ifdef" / "ifndef") Spacing Identifier ConditionalLines
         ("`elsif" Spacing Identifier ConditionalLines)*
         ("`else" ConditionalLines)?
         "`endif"
         */
        EState top = stStateStk.peek();
        switch (nstate) {
            case eIfdef:    //fall through
            case eIfndef:
                stStateStk.push(nstate);
                break;
            case eElse: //fall through
            case eElseif:
                if ((EState.eUnconditional == top) || (EState.eElse == top)) {
                    setError("VPP-UNEXPECT-2", tok);
                } else {
                    stStateStk.push(nstate);
                }
                break;
            case eEndif:
                if (EState.eUnconditional == top) {
                    setError("VPP-UNEXPECT-3", tok);
                } else {
                    while (true) {
                        assert !stStateStk.empty();
                        top = stStateStk.pop();
                        if (EState.eIfdef == top || EState.eIfndef == top) {
                            break;
                        }
                    }
                    //never empty since minimum is eUnconditional
                    assert !stStateStk.empty();
                }
                break;
            default:
                assert false;
        }
        return !hasError();
    }

    private boolean nextState() {
        return nextState(m_next, m_tok);
    }

    private boolean nextState(final String tok) {
        return nextState(m_next, tok);
    }

    private EState m_next = null;
    private String m_tok = null, m_sid = null;

    /**
     * Parse conditional statement. It is assumed the `ifdef, ... has been
     * detected (but not accepted) upon entry. Thus, if we have any issue here,
     * we will mark as parse error.
     *
     * @return true on success; else false.
     */
    @Override
    protected boolean accepti() {
        PrioritizedChoice pc1 = new PrioritizedChoice(
                new CharSeq("`ifdef"), new CharSeq("`ifndef"),
                new CharSeq("`elsif"));
        boolean match = (null != (pc1 = match(pc1)));
        if (match) {
            m_tok = pc1.toString();
            m_next = (0 == pc1.whichAccepted())
                    ? EState.eIfdef
                    : ((1 == pc1.whichAccepted())
                            ? EState.eIfndef : EState.eElseif);
            match = (new Spacing()).acceptTrue();
            if (match) {
                Identifier id = new Identifier();
                match = (null != (id = match(id)));
                if (match) {
                    if (!nextState()) {
                        return false;
                    }
                    m_sid = id.toString();
                }
            }
            if (!match) {
                setParseError();
                return false;
            }
        } else if (matchTrue(new CharSeq("`else"))) {
            m_next = EState.eElse;
            if (!nextState("`else")) {
                return false;
            }
        } else if (matchTrue(new CharSeq("`endif"))) {
            m_next = EState.eEndif;
            if (!nextState("`endif")) {
                return false;
            }
        } else {
            setParseError();
            return false;
        }
        final Helper main = Helper.getTheOne();
        switch (m_next) {
            case eIfdef:
                main.ticIfdef(m_sid);
                break;
            case eIfndef:
                main.ticIfndef(m_sid);
                break;
            case eElseif:
                main.ticElsif(m_sid);
                break;
            case eElse:
                main.ticElse();
                break;
            case eEndif:
                main.ticEndif();
                break;
            default:
                assert false;
        }
        main.replace(super.getStartMark());
        return true;
    }

    @Override
    public Acceptor create() {
        return new TicConditional();
    }

    private TicConditional() {
        super(null);
    }
}
