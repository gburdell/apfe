
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class case_inside_item extends Acceptor implements NonTerminal, ITokenCodes {

    public case_inside_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new open_range_list(),
new Terminal(COLON),
new statement_or_null()),
new Sequence(new Terminal(DEFAULT_K),
new Optional(new Terminal(COLON)) ,
new statement_or_null())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public case_inside_item create() {
        return new case_inside_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


