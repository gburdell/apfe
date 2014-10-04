
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class controlled_timing_check_event extends Acceptor implements NonTerminal, ITokenCodes {

    public controlled_timing_check_event() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new timing_check_event_control(),
new specify_terminal_descriptor(),
new Optional(new Sequence(new Terminal(AND3),
new timing_check_condition())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public controlled_timing_check_event create() {
        return new controlled_timing_check_event();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


