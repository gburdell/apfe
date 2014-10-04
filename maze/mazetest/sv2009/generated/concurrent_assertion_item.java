
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class concurrent_assertion_item extends Acceptor implements NonTerminal, ITokenCodes {

    public concurrent_assertion_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Optional(new Sequence(new block_identifier(),
new Terminal(COLON))) ,
new concurrent_assertion_statement()),
new checker_instantiation()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public concurrent_assertion_item create() {
        return new concurrent_assertion_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


