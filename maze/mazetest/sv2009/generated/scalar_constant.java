
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class scalar_constant extends Acceptor implements NonTerminal, ITokenCodes {

    public scalar_constant() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Terminal(NUMBER) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public scalar_constant create() {
        return new scalar_constant();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


