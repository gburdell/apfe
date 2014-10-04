
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class boolean_abbrev extends Acceptor implements NonTerminal, ITokenCodes {

    public boolean_abbrev() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new consecutive_repetition(),
new non_consecutive_repetition(),
new goto_repetition()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public boolean_abbrev create() {
        return new boolean_abbrev();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


