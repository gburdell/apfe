
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class property_lvar_port_direction extends Acceptor implements NonTerminal, ITokenCodes {

    public property_lvar_port_direction() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Terminal(INPUT_K) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public property_lvar_port_direction create() {
        return new property_lvar_port_direction();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


