
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class cycle_delay_range extends Acceptor implements NonTerminal, ITokenCodes {

    public cycle_delay_range() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(POUND2),
new Terminal(LBRACK),
new cycle_delay_const_range_expression(),
new Terminal(RBRACK)),
new Sequence(new Terminal(POUND2),
new Terminal(LBRACK),
new Terminal(STAR),
new Terminal(RBRACK)),
new Sequence(new Terminal(POUND2),
new Terminal(LBRACK),
new Terminal(PLUS),
new Terminal(RBRACK)),
new Sequence(new Terminal(POUND2),
new constant_primary())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public cycle_delay_range create() {
        return new cycle_delay_range();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


