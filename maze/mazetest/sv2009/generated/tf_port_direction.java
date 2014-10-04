
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class tf_port_direction extends Acceptor implements NonTerminal, ITokenCodes {

    public tf_port_direction() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new port_direction(),
new Sequence(new Terminal(CONST_K),
new Terminal(REF_K))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public tf_port_direction create() {
        return new tf_port_direction();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


