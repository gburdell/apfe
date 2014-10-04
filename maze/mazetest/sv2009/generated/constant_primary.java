
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class constant_primary extends Acceptor implements NonTerminal, ITokenCodes {

    public constant_primary() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new constant_primary_HEAD(),
new Repetition(new constant_primary_TAIL()) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public constant_primary create() {
        return new constant_primary();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


