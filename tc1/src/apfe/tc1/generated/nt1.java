
package apfe.tc1.generated ;


import java.util.List;
import java.util.LinkedList;

import apfe.runtime.*;
import apfe.tc1.*;





public  class nt1 extends Acceptor implements ITokenCodes {

    public nt1() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new Sequence(new nt2(),
new Terminal(MODULE_K),
new Terminal(SEMI)) ;
break;
case 1:
acc = new Sequence(new nt2(),
new Terminal(ENDMODULE_K),
new Terminal(SEMI)) ;
break;
case 2:
acc = new Sequence(new nt2(),
new Terminal(ALWAYS_K),
new Terminal(SEMI)) ;
break;
}
return acc;
}
}) ;
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
    public nt1 create() {
        return new nt1();
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
     * Memoize for all instances of nt1.
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


