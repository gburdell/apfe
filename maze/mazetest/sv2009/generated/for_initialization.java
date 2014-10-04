
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class for_initialization extends Acceptor implements NonTerminal, ITokenCodes {

    public for_initialization() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new list_of_variable_assignments(),
new Sequence(new for_variable_declaration(),
new Repetition(new Sequence(new Terminal(COMMA),
new for_variable_declaration())) )) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public for_initialization create() {
        return new for_initialization();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


