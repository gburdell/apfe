
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class clocking_item extends Acceptor implements NonTerminal, ITokenCodes {

    public clocking_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(DEFAULT_K),
new default_skew(),
new Terminal(SEMI)),
new Sequence(new clocking_direction(),
new list_of_clocking_decl_assign(),
new Terminal(SEMI)),
new Sequence(new Repetition(new attribute_instance()) ,
new assertion_item_declaration())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public clocking_item create() {
        return new clocking_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


