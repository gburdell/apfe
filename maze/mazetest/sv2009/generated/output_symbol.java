
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class output_symbol extends Acceptor implements NonTerminal, ITokenCodes {

    public output_symbol() {
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
    public output_symbol create() {
        return new output_symbol();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


