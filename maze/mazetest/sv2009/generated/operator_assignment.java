
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class operator_assignment extends Acceptor implements NonTerminal, ITokenCodes {

    public operator_assignment() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new variable_lvalue(),
new assignment_operator(),
new expression()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public operator_assignment create() {
        return new operator_assignment();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


