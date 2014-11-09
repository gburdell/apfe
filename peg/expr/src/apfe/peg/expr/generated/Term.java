
package apfe.peg.expr.generated ;


import java.util.List;
import java.util.LinkedList;

import apfe.runtime.*;





public  class Term extends LeftRecursiveAcceptor {

    public Term() {
    }

    private Term(boolean isDRR) {
        super(isDRR);
    }

    @Override
    protected int getNonRecursiveChoiceIx() {
        return 1;
    }

    @Override
    public Acceptor create() {
        return new Term();
    }

    @Override
    public Acceptor getChoice(int ix) {
        Acceptor matcher = null  ;
		switch(ix) {
		case 0:
matcher = new Sequence(this,
new CharClass(CharClass.matchOneOf('*'),
CharClass.matchOneOf('/')),
new Factor()) ;
break;
case 1:
matcher = new Factor() ;
break;

		}
        //Keep track so if we growSeed()
        m_lastIx = ix;
        m_lastChoice = matcher;
        return matcher;
    }

    @Override
    protected boolean accepti() {
		boolean match = super.accepti();

        if (match && (null != getListeners())) {
            for (Listener cb : getListeners()) {
                cb.onAccept(m_lastChoice);
            }
        }

        return match;
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
     * Memoize for all instances of Term.
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


