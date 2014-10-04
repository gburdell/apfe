
package apfe.maze.tc1.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.tc1.*;



public  class port_direction extends Acceptor implements NonTerminal, ITokenCodes {

    public port_direction() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Terminal(INPUT_K),
new Terminal(OUTPUT_K),
new Terminal(INOUT_K),
new Terminal(REF_K)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public port_direction create() {
        return new port_direction();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


