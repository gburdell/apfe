
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class expression_or_dist extends Acceptor implements NonTerminal, ITokenCodes {

    public expression_or_dist() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new expression(),
new Optional(new Sequence(new Terminal(DIST_K),
new Terminal(LCURLY),
new dist_list(),
new Terminal(RCURLY))) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public expression_or_dist create() {
        return new expression_or_dist();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


