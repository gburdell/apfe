
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class select_bins_or_empty extends Acceptor implements NonTerminal, ITokenCodes {

    public select_bins_or_empty() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(LCURLY),
new Repetition(new Sequence(new bins_selection_or_option(),
new Terminal(SEMI))) ,
new Terminal(RCURLY)),
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public select_bins_or_empty create() {
        return new select_bins_or_empty();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


