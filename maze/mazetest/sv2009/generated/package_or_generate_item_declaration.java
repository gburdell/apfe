
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class package_or_generate_item_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public package_or_generate_item_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new net_declaration(),
new data_declaration(),
new task_declaration(),
new function_declaration(),
new checker_declaration(),
new dpi_import_export(),
new extern_constraint_declaration(),
new class_declaration(),
new class_constructor_declaration(),
new Sequence(new local_parameter_declaration(),
new Terminal(SEMI)),
new Sequence(new parameter_declaration(),
new Terminal(SEMI)),
new covergroup_declaration(),
new overload_declaration(),
new assertion_item_declaration(),
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public package_or_generate_item_declaration create() {
        return new package_or_generate_item_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


