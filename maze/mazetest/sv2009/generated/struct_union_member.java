
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class struct_union_member extends Acceptor implements NonTerminal, ITokenCodes {

    public struct_union_member() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Repetition(new attribute_instance()) ,
new Optional(new random_qualifier()) ,
new data_type_or_void(),
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
    public struct_union_member create() {
        return new struct_union_member();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


