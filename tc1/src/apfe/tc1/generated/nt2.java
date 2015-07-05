package apfe.tc1.generated;

import java.util.List;
import java.util.LinkedList;

import apfe.runtime.*;
import apfe.tc1.*;

public class nt2 extends Acceptor implements ITokenCodes {

    public nt2() {
    }

    @Override
    protected boolean accepti() {
        Acceptor matcher = new Sequence(new Terminal(IDENT),
                new Repetition(new Sequence(new Terminal(COMMA),
                                new Terminal(IDENT)), Repetition.ERepeat.eZeroOrMore));
        Acceptor accepted = match(matcher);
        boolean match = (null != accepted);

        if (match && (null != getListeners())) {
            for (Listener cb : getListeners()) {
                cb.onAccept(accepted);
            }
        }

        return match;
    }

    @Override
    public nt2 create() {
        return new nt2();
    }

    //Begin memoize
    @Override
    protected void memoize(Marker mark, Marker endMark) {
        stMemo.add(mark, this, endMark);
    }

    @Override
    protected Memoize.Data hasMemoized(Marker mark) {
        return stMemo.memoized(mark);
    }
    /**
     * Memoize for all instances of nt2.
     */
    private static final Memoize stMemo = new Memoize();
	//End memoize

    //Begin Listener
    static //@Override
    public void addListener(final Listener listener) {
        if (null == stListeners) {
            stListeners = new LinkedList<>();
        }
        stListeners.add(listener);
    }

    @Override
    protected Iterable<Listener> getListeners() {
        return stListeners;
    }

    private static List<Listener> stListeners;
    //End Listener

}
