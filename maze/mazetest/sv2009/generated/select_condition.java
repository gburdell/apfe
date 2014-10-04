
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class select_condition extends Acceptor implements NonTerminal, ITokenCodes {

    public select_condition() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(BINSOF_K),
new Terminal(LPAREN),
new bins_expression(),
new Terminal(RPAREN),
new Optional(new Sequence(new Terminal(INTERSECT_K),
new Terminal(LCURLY),
new open_range_list(),
new Terminal(RCURLY))) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public select_condition create() {
        return new select_condition();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


