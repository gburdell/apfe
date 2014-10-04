
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class interface_port_header extends Acceptor implements NonTerminal, ITokenCodes {

    public interface_port_header() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(INTERFACE_K),
new Optional(new Sequence(new Terminal(DOT),
new modport_identifier())) ),
new Sequence(new interface_identifier(),
new Optional(new Sequence(new Terminal(DOT),
new modport_identifier())) )) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public interface_port_header create() {
        return new interface_port_header();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


