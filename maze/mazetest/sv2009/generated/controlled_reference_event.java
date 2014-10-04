
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class controlled_reference_event extends Acceptor implements NonTerminal, ITokenCodes {

    public controlled_reference_event() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new controlled_timing_check_event() ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public controlled_reference_event create() {
        return new controlled_reference_event();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


