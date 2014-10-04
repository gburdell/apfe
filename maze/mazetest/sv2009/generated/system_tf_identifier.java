
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class system_tf_identifier extends Acceptor implements NonTerminal, ITokenCodes {

    public system_tf_identifier() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Terminal(SYSTEM_IDENT) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public system_tf_identifier create() {
        return new system_tf_identifier();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


