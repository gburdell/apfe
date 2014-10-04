
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class unsized_dimension extends Acceptor implements NonTerminal, ITokenCodes {

    public unsized_dimension() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(LBRACK),
new Terminal(RBRACK)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public unsized_dimension create() {
        return new unsized_dimension();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


