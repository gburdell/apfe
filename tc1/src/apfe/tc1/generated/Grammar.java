package apfe.tc1.generated;

import java.util.List;
import java.util.LinkedList;

import apfe.runtime.*;
import apfe.tc1.*;

public class Grammar extends Acceptor implements ITokenCodes {

    public Grammar() {
    }

    @Override
    protected boolean accepti() {
        Acceptor matcher = new source_text();
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
    public Grammar create() {
        return new Grammar();
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
     * Memoize for all instances of Grammar.
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
