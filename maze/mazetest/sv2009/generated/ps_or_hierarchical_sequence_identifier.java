
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class ps_or_hierarchical_sequence_identifier extends Acceptor implements NonTerminal, ITokenCodes {

    public ps_or_hierarchical_sequence_identifier() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Optional(new package_scope()) ,
new sequence_identifier()),
new hierarchical_sequence_identifier()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public ps_or_hierarchical_sequence_identifier create() {
        return new ps_or_hierarchical_sequence_identifier();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


