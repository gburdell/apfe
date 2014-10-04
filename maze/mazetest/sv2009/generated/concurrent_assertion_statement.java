
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class concurrent_assertion_statement extends Acceptor implements NonTerminal, ITokenCodes {

    public concurrent_assertion_statement() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new assert_property_statement(),
new assume_property_statement(),
new cover_property_statement(),
new cover_sequence_statement(),
new restrict_property_statement()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public concurrent_assertion_statement create() {
        return new concurrent_assertion_statement();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


