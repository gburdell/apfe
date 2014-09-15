
package apfe.maze.tc2.generated ;


import apfe.maze.runtime.*;
import apfe.maze.tc2.*;



public  class b extends Acceptor implements NonTerminal, ITokenCodes {

    public b() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Repetition(new Terminal(B_K))  ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public b create() {
        return new b();
    }


}


