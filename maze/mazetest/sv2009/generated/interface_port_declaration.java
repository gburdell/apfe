
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class interface_port_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public interface_port_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new interface_identifier(),
new list_of_interface_identifiers()),
new Sequence(new interface_identifier(),
new Terminal(DOT),
new modport_identifier(),
new list_of_interface_identifiers())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public interface_port_declaration create() {
        return new interface_port_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


