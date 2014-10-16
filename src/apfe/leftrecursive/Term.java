
package apfe.leftrecursive ;


import apfe.runtime.*;
//import apfe.leftrecursive.*;




public  class Term extends LeftRecursiveAcceptor {

    public Term() {
    }

	//Use for indirect left-recursion detection
    @Override
    public String getNonTermID() {
        return getClass().getSimpleName();
    }

    private Term(boolean isDRR) {
        super(isDRR);
    }

    @Override
    protected int getNonRecursiveChoiceIx() {
        return 2;
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
new CharSeq('+'),
new Term()) ;
break;
case 1:
matcher = new Sequence(this,
new CharSeq('-'),
new Term(true)) ;
break;
case 2:
matcher = new Fact() ;
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

        return match;
    }

 


}


