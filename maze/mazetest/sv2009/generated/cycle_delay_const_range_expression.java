
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class cycle_delay_const_range_expression extends Acceptor implements NonTerminal, ITokenCodes {

    public cycle_delay_const_range_expression() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new constant_expression(),
new Terminal(COLON),
new constant_expression()),
new Sequence(new constant_expression(),
new Terminal(COLON),
new Terminal(DOLLAR))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public cycle_delay_const_range_expression create() {
        return new cycle_delay_const_range_expression();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


