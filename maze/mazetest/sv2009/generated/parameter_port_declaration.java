
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class parameter_port_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public parameter_port_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new local_parameter_declaration(),
new Sequence(new data_type(),
new list_of_param_assignments()),
new Sequence(new Terminal(TYPE_K),
new list_of_type_assignments()),
new parameter_declaration()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public parameter_port_declaration create() {
        return new parameter_port_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


