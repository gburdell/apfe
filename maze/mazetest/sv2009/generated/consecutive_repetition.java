
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class consecutive_repetition extends Acceptor implements NonTerminal, ITokenCodes {

    public consecutive_repetition() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(LBRACK),
new Terminal(STAR),
new const_or_range_expression(),
new Terminal(RBRACK)),
new Sequence(new Terminal(LBRACK),
new Terminal(STAR),
new Terminal(RBRACK)),
new Sequence(new Terminal(LBRACK),
new Terminal(PLUS),
new Terminal(RBRACK))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public consecutive_repetition create() {
        return new consecutive_repetition();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


