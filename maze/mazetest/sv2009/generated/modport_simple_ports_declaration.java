
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class modport_simple_ports_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public modport_simple_ports_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new port_direction(),
new modport_simple_port(),
new Repetition(new Sequence(new Terminal(COMMA),
new modport_simple_port())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public modport_simple_ports_declaration create() {
        return new modport_simple_ports_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


