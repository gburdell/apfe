
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class gate_terminal extends Acceptor implements NonTerminal, ITokenCodes {

    public gate_terminal() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new expression(),
new net_lvalue()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public gate_terminal create() {
        return new gate_terminal();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


