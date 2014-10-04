
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class edge_descriptor extends Acceptor implements NonTerminal, ITokenCodes {

    public edge_descriptor() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Terminal(STRING_LITERAL) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public edge_descriptor create() {
        return new edge_descriptor();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


