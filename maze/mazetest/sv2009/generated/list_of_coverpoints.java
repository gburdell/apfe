
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class list_of_coverpoints extends Acceptor implements NonTerminal, ITokenCodes {

    public list_of_coverpoints() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new cross_item(),
new Terminal(COMMA),
new cross_item(),
new Repetition(new Sequence(new Terminal(COMMA),
new cross_item())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public list_of_coverpoints create() {
        return new list_of_coverpoints();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


