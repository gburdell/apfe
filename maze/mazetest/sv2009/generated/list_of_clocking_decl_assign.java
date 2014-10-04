
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class list_of_clocking_decl_assign extends Acceptor implements NonTerminal, ITokenCodes {

    public list_of_clocking_decl_assign() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new clocking_decl_assign(),
new Repetition(new Sequence(new Terminal(COMMA),
new clocking_decl_assign())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public list_of_clocking_decl_assign create() {
        return new list_of_clocking_decl_assign();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


