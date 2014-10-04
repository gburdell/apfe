
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class bind_target_scope extends Acceptor implements NonTerminal, ITokenCodes {

    public bind_target_scope() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new module_identifier(),
new interface_identifier()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public bind_target_scope create() {
        return new bind_target_scope();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


