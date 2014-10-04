
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class list_of_port_connections extends Acceptor implements NonTerminal, ITokenCodes {

    public list_of_port_connections() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new named_port_connection(),
new Repetition(new Sequence(new Terminal(COMMA),
new named_port_connection())) ),
new Sequence(new ordered_port_connection(),
new Repetition(new Sequence(new Terminal(COMMA),
new ordered_port_connection())) )) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public list_of_port_connections create() {
        return new list_of_port_connections();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


