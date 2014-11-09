
package apfe.peg.expr.generated ;


import java.util.List;
import java.util.LinkedList;

import apfe.runtime.*;





public  class Unary extends Acceptor  {

    public Unary() {
    }

	public Acceptor getAccepted() {
		return m_accepted;
	}

	private Acceptor m_accepted;

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new CharSeq('+') ;
break;
case 1:
acc = new CharSeq('-') ;
break;
}
return acc;
}
}) ;
		m_accepted = match(matcher);
		boolean match = (null != m_accepted);

        if (match && (null != getListeners())) {
            for (Listener cb : getListeners()) {
                cb.onAccept(m_accepted);
            }
        }

        return match;
    }

    @Override
    public Unary create() {
        return new Unary();
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
     * Memoize for all instances of Unary.
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


