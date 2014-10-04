
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class remain_active_flag extends Acceptor implements NonTerminal, ITokenCodes {

    public remain_active_flag() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new constant_mintypmax_expression() ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public remain_active_flag create() {
        return new remain_active_flag();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


