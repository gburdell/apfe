
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class local_parameter_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public local_parameter_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(LOCALPARAM_K),
new list_of_param_assignments()),
new Sequence(new Terminal(LOCALPARAM_K),
new data_type_or_implicit(),
new list_of_param_assignments()),
new Sequence(new Terminal(LOCALPARAM_K),
new Terminal(TYPE_K),
new list_of_type_assignments())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public local_parameter_declaration create() {
        return new local_parameter_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


