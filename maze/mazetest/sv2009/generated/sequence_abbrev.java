
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class sequence_abbrev extends Acceptor implements NonTerminal, ITokenCodes {

    public sequence_abbrev() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new consecutive_repetition() ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public sequence_abbrev create() {
        return new sequence_abbrev();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


