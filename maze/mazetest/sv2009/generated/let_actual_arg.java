
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class let_actual_arg extends Acceptor implements NonTerminal, ITokenCodes {

    public let_actual_arg() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new expression() ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public let_actual_arg create() {
        return new let_actual_arg();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


