
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class timing_check_event_control extends Acceptor implements NonTerminal, ITokenCodes {

    public timing_check_event_control() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Terminal(POSEDGE_K),
new Terminal(NEGEDGE_K),
new Terminal(EDGE_K),
new edge_control_specifier()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public timing_check_event_control create() {
        return new timing_check_event_control();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


