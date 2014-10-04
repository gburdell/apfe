
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class simple_identifier extends Acceptor implements NonTerminal, ITokenCodes {

    public simple_identifier() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Terminal(IDENT) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public simple_identifier create() {
        return new simple_identifier();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


