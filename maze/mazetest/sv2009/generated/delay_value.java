
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class delay_value extends Acceptor implements NonTerminal, ITokenCodes {

    public delay_value() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Terminal(W1STEP_K),
new Terminal(NUMBER),
new Terminal(UNSIGNED_NUMBER),
new ps_identifier(),
new Terminal(TIME_LITERAL)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public delay_value create() {
        return new delay_value();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


