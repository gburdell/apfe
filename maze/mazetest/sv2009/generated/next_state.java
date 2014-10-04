
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class next_state extends Acceptor implements NonTerminal, ITokenCodes {

    public next_state() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new output_symbol(),
new Terminal(MINUS)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public next_state create() {
        return new next_state();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


