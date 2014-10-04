
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class constraint_set extends Acceptor implements NonTerminal, ITokenCodes {

    public constraint_set() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new constraint_expression(),
new Sequence(new Terminal(LCURLY),
new Repetition(new constraint_expression()) ,
new Terminal(RCURLY))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public constraint_set create() {
        return new constraint_set();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


