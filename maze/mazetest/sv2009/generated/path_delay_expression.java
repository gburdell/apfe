
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class path_delay_expression extends Acceptor implements NonTerminal, ITokenCodes {

    public path_delay_expression() {
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
    public path_delay_expression create() {
        return new path_delay_expression();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


