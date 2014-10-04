
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class generate_item extends Acceptor implements NonTerminal, ITokenCodes {

    public generate_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new module_or_generate_item(),
new interface_or_generate_item(),
new checker_or_generate_item()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public generate_item create() {
        return new generate_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


