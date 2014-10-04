
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class index_variable_identifier extends Acceptor implements NonTerminal, ITokenCodes {

    public index_variable_identifier() {
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
    public index_variable_identifier create() {
        return new index_variable_identifier();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


