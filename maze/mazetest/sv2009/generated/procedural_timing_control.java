
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class procedural_timing_control extends Acceptor implements NonTerminal, ITokenCodes {

    public procedural_timing_control() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new delay_control(),
new event_control(),
new cycle_delay()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public procedural_timing_control create() {
        return new procedural_timing_control();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


