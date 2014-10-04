
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class procedural_continuous_assignment extends Acceptor implements NonTerminal, ITokenCodes {

    public procedural_continuous_assignment() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(ASSIGN_K),
new variable_assignment()),
new Sequence(new Terminal(DEASSIGN_K),
new variable_lvalue()),
new Sequence(new Terminal(FORCE_K),
new variable_assignment()),
new Sequence(new Terminal(FORCE_K),
new net_assignment()),
new Sequence(new Terminal(RELEASE_K),
new variable_lvalue()),
new Sequence(new Terminal(RELEASE_K),
new net_lvalue())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public procedural_continuous_assignment create() {
        return new procedural_continuous_assignment();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


