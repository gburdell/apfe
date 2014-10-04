
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class virtual_interface_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public virtual_interface_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(VIRTUAL_K),
new Optional(new Terminal(INTERFACE_K)) ,
new interface_identifier(),
new Optional(new parameter_value_assignment()) ,
new Optional(new Sequence(new Terminal(DOT),
new modport_identifier())) ,
new list_of_virtual_interface_decl(),
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public virtual_interface_declaration create() {
        return new virtual_interface_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


