
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class always_construct extends Acceptor implements NonTerminal, ITokenCodes {

    public always_construct() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new always_keyword(),
new statement()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public always_construct create() {
        return new always_construct();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


