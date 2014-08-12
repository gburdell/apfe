@9@
package @1@ ;

@6.1@
import java.util.List;
import java.util.LinkedList;
@6.1@
import apfe.runtime.*;
import apfe.sv2009.*;
@4.1@
import apfe.runtime.CharBuffer.Marker;
@4.1@
@9@

@10@
public @11@ class @2@ extends @3@ {

    public @2@() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = @8@ ;
		Acceptor accepted = match(matcher);
		boolean match = (null != accepted);
@6.2@
        if (match && (null != getListeners())) {
            for (Listener cb : getListeners()) {
                cb.onAccept(accepted);
            }
        }
@6.2@
        return match;
    }

    @Override
    public @2@ create() {
        return new @2@();
    }

@4.2@ //Begin memoize
    @Override
    protected void memoize(Marker mark, Marker endMark) {
        stMemo.add(mark, this, endMark);
    }

    @Override
    protected Memoize.Data hasMemoized(Marker mark) {
        return stMemo.memoized(mark);
    }
    /**
     * Memoize for all instances of @2@.
     */
    private static final Memoize stMemo = new Memoize();
	//End memoize
@4.2@ 

@6.3@
	//Begin Listener
	@Override
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
@6.3@
}
@10@
