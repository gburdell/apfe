
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class sequential_entry extends Acceptor implements NonTerminal, ITokenCodes {

    public sequential_entry() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new seq_input_list(),
new Terminal(COLON),
new current_state(),
new Terminal(COLON),
new next_state(),
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public sequential_entry create() {
        return new sequential_entry();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


