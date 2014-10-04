
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class anonymous_program_item extends Acceptor implements NonTerminal, ITokenCodes {

    public anonymous_program_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new task_declaration(),
new function_declaration(),
new class_declaration(),
new covergroup_declaration(),
new class_constructor_declaration(),
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public anonymous_program_item create() {
        return new anonymous_program_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


