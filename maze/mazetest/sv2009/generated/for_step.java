
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class for_step extends Acceptor implements NonTerminal, ITokenCodes {

    public for_step() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new for_step_assignment(),
new Repetition(new Sequence(new Terminal(COMMA),
new for_step_assignment())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public for_step create() {
        return new for_step();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


