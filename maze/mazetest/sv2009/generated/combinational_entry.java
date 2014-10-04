
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class combinational_entry extends Acceptor implements NonTerminal, ITokenCodes {

    public combinational_entry() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new level_input_list(),
new Terminal(COLON),
new output_symbol(),
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public combinational_entry create() {
        return new combinational_entry();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


