
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class trans_set extends Acceptor implements NonTerminal, ITokenCodes {

    public trans_set() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new trans_range_list(),
new Repetition(new Sequence(new Terminal(EQ_GT),
new trans_range_list())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public trans_set create() {
        return new trans_set();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


