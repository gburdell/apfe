
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class checker_or_generate_item extends Acceptor implements NonTerminal, ITokenCodes {

    public checker_or_generate_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new checker_or_generate_item_declaration(),
new initial_construct(),
new checker_always_construct(),
new final_construct(),
new assertion_item(),
new checker_generate_item()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public checker_or_generate_item create() {
        return new checker_or_generate_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


