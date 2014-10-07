
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class start_edge_offset extends Acceptor implements NonTerminal, ITokenCodes {

    public start_edge_offset() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new mintypmax_expression() ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public start_edge_offset create() {
        return new start_edge_offset();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}

