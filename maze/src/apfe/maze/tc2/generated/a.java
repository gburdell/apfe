
package apfe.maze.tc2.generated ;


import apfe.maze.runtime.*;
import apfe.maze.tc2.*;



public  class a extends Acceptor implements NonTerminal, ITokenCodes {

    public a() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(A_K),
new Terminal(A_K),
new Terminal(A_K)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public a create() {
        return new a();
    }


}


