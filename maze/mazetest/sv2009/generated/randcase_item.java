
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class randcase_item extends Acceptor implements NonTerminal, ITokenCodes {

    public randcase_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new expression(),
new Terminal(COLON),
new statement_or_null()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public randcase_item create() {
        return new randcase_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


