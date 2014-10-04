
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class deferred_immediate_assertion_item extends Acceptor implements NonTerminal, ITokenCodes {

    public deferred_immediate_assertion_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Optional(new Sequence(new block_identifier(),
new Terminal(COLON))) ,
new deferred_immediate_assertion_statement()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public deferred_immediate_assertion_item create() {
        return new deferred_immediate_assertion_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


