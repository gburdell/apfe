
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class block_event_expression_HEAD extends Acceptor implements NonTerminal, ITokenCodes {

    public block_event_expression_HEAD() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(BEGIN_K),
new hierarchical_btf_identifier()),
new Sequence(new Terminal(END_K),
new hierarchical_btf_identifier())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public block_event_expression_HEAD create() {
        return new block_event_expression_HEAD();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


