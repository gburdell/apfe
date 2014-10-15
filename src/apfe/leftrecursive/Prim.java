
package apfe.leftrecursive ;


import apfe.runtime.*;
//import apfe.leftrecursive.*;




public  class Prim extends Acceptor {

    public Prim() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new Sequence(new CharSeq('('),
new Term(),
new CharSeq(')')) ;
break;
case 1:
acc = new Int() ;
break;
}
return acc;
}
}) ;
		Acceptor accepted = match(matcher);
		boolean match = (null != accepted);

        return match;
    }

    @Override
    public Prim create() {
        return new Prim();
    }

 


}


