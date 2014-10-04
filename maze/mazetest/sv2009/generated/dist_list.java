
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class dist_list extends Acceptor implements NonTerminal, ITokenCodes {

    public dist_list() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new dist_item(),
new Repetition(new Sequence(new Terminal(COMMA),
new dist_item())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public dist_list create() {
        return new dist_list();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


