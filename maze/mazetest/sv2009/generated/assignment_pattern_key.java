
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class assignment_pattern_key extends Acceptor implements NonTerminal, ITokenCodes {

    public assignment_pattern_key() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new simple_type(),
new Terminal(DEFAULT_K)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public assignment_pattern_key create() {
        return new assignment_pattern_key();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


