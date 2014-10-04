
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class unique_priority extends Acceptor implements NonTerminal, ITokenCodes {

    public unique_priority() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Terminal(UNIQUE_K),
new Terminal(UNIQUE0_K),
new Terminal(PRIORITY_K)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public unique_priority create() {
        return new unique_priority();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


