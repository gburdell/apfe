
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class rs_case_item extends Acceptor implements NonTerminal, ITokenCodes {

    public rs_case_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(DEFAULT_K),
new Optional(new Terminal(COLON)) ,
new production_item(),
new Terminal(SEMI)),
new Sequence(new case_item_expression(),
new Repetition(new Sequence(new Terminal(COMMA),
new case_item_expression())) ,
new Terminal(COLON),
new production_item(),
new Terminal(SEMI))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public rs_case_item create() {
        return new rs_case_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


