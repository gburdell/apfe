
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class range_expression extends Acceptor implements NonTerminal, ITokenCodes {

    public range_expression() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new expression(),
new part_select_range()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public range_expression create() {
        return new range_expression();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


