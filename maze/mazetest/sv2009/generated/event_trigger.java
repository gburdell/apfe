
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class event_trigger extends Acceptor implements NonTerminal, ITokenCodes {

    public event_trigger() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(MINUS_GT2),
new Optional(new delay_or_event_control()) ,
new hierarchical_event_identifier(),
new Terminal(SEMI)),
new Sequence(new Terminal(MINUS_GT),
new hierarchical_event_identifier(),
new Terminal(SEMI))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public event_trigger create() {
        return new event_trigger();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


