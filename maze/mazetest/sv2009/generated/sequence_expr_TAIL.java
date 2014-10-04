
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class sequence_expr_TAIL extends Acceptor implements NonTerminal, ITokenCodes {

    public sequence_expr_TAIL() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new cycle_delay_range(),
new sequence_expr()),
new Sequence(new Terminal(AND_K),
new sequence_expr()),
new Sequence(new Terminal(INTERSECT_K),
new sequence_expr()),
new Sequence(new Terminal(OR_K),
new sequence_expr()),
new Sequence(new Terminal(WITHIN_K),
new sequence_expr())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public sequence_expr_TAIL create() {
        return new sequence_expr_TAIL();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


