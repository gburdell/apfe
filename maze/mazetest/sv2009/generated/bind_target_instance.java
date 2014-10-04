
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class bind_target_instance extends Acceptor implements NonTerminal, ITokenCodes {

    public bind_target_instance() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new hierarchical_identifier(),
new constant_bit_select()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public bind_target_instance create() {
        return new bind_target_instance();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


