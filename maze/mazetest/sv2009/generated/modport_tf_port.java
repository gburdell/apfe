
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class modport_tf_port extends Acceptor implements NonTerminal, ITokenCodes {

    public modport_tf_port() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new method_prototype(),
new tf_identifier()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public modport_tf_port create() {
        return new modport_tf_port();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


