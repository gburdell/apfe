
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class list_of_udp_port_identifiers extends Acceptor implements NonTerminal, ITokenCodes {

    public list_of_udp_port_identifiers() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new port_identifier(),
new Repetition(new Sequence(new Terminal(COMMA),
new port_identifier())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public list_of_udp_port_identifiers create() {
        return new list_of_udp_port_identifiers();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


