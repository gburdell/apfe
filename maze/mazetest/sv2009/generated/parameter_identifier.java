
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class parameter_identifier extends Acceptor implements NonTerminal, ITokenCodes {

    public parameter_identifier() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new identifier() ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public parameter_identifier create() {
        return new parameter_identifier();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


