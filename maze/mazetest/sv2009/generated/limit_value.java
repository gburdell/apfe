
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class limit_value extends Acceptor implements NonTerminal, ITokenCodes {

    public limit_value() {
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
    public limit_value create() {
        return new limit_value();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


