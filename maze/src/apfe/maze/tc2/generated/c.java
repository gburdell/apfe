
package apfe.maze.tc2.generated ;


import apfe.maze.runtime.*;
import apfe.maze.tc2.*;



public  class c extends Acceptor implements NonTerminal, ITokenCodes {

    public c() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Terminal(C_K) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public c create() {
        return new c();
    }


}


