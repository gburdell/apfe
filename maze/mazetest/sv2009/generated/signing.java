
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class signing extends Acceptor implements NonTerminal, ITokenCodes {

    public signing() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Terminal(SIGNED_K),
new Terminal(UNSIGNED_K)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public signing create() {
        return new signing();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


