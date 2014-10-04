
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class cell_identifier extends Acceptor implements NonTerminal, ITokenCodes {

    public cell_identifier() {
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
    public cell_identifier create() {
        return new cell_identifier();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


