
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class tz_path_delay_expression extends Acceptor implements NonTerminal, ITokenCodes {

    public tz_path_delay_expression() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new path_delay_expression() ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public tz_path_delay_expression create() {
        return new tz_path_delay_expression();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


