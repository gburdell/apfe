
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class case_item extends Acceptor implements NonTerminal, ITokenCodes {

    public case_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new case_item_expression(),
new Repetition(new Sequence(new Terminal(COMMA),
new case_item_expression())) ,
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
    public case_item create() {
        return new case_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


