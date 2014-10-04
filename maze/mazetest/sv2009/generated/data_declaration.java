
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class data_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public data_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Optional(new Terminal(CONST_K)) ,
new Optional(new Terminal(VAR_K)) ,
new Optional(new lifetime()) ,
new data_type_or_implicit(),
new list_of_variable_decl_assignments(),
new Terminal(SEMI)),
new type_declaration(),
new package_import_declaration(),
new virtual_interface_declaration()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public data_declaration create() {
        return new data_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


