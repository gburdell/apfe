
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class assertion_variable_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public assertion_variable_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new var_data_type(),
new list_of_variable_decl_assignments(),
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public assertion_variable_declaration create() {
        return new assertion_variable_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


