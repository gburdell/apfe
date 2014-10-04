
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class list_of_variable_assignments extends Acceptor implements NonTerminal, ITokenCodes {

    public list_of_variable_assignments() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new variable_assignment(),
new Repetition(new Sequence(new Terminal(COMMA),
new variable_assignment())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public list_of_variable_assignments create() {
        return new list_of_variable_assignments();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


