
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class rs_prod extends Acceptor implements NonTerminal, ITokenCodes {

    public rs_prod() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new rs_code_block(),
new rs_if_else(),
new rs_repeat(),
new rs_case(),
new production_item()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public rs_prod create() {
        return new rs_prod();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


