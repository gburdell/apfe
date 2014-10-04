
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class timing_check_condition extends Acceptor implements NonTerminal, ITokenCodes {

    public timing_check_condition() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(LPAREN),
new scalar_timing_check_condition(),
new Terminal(RPAREN)),
new scalar_timing_check_condition()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public timing_check_condition create() {
        return new timing_check_condition();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


