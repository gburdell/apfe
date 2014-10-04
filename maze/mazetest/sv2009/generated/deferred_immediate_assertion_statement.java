
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class deferred_immediate_assertion_statement extends Acceptor implements NonTerminal, ITokenCodes {

    public deferred_immediate_assertion_statement() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new deferred_immediate_assert_statement(),
new deferred_immediate_assume_statement(),
new deferred_immediate_cover_statement()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public deferred_immediate_assertion_statement create() {
        return new deferred_immediate_assertion_statement();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


