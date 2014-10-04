
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class event_based_flag extends Acceptor implements NonTerminal, ITokenCodes {

    public event_based_flag() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new constant_expression() ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public event_based_flag create() {
        return new event_based_flag();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


