
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class event_control extends Acceptor implements NonTerminal, ITokenCodes {

    public event_control() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(AT),
new Terminal(LPAREN),
new event_expression(),
new Terminal(RPAREN)),
new Terminal(AT_STAR),
new Sequence(new Terminal(AT),
new Terminal(LPAREN),
new Terminal(STAR),
new Terminal(RPAREN)),
new Sequence(new Terminal(AT),
new hierarchical_event_identifier()),
new Sequence(new Terminal(AT),
new ps_or_hierarchical_sequence_identifier())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public event_control create() {
        return new event_control();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


