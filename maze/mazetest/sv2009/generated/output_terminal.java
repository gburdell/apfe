
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class output_terminal extends Acceptor implements NonTerminal, ITokenCodes {

    public output_terminal() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new net_lvalue() ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public output_terminal create() {
        return new output_terminal();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


