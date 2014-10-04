
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class port_reference extends Acceptor implements NonTerminal, ITokenCodes {

    public port_reference() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new port_identifier(),
new constant_select()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public port_reference create() {
        return new port_reference();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


