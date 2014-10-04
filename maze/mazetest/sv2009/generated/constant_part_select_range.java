
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class constant_part_select_range extends Acceptor implements NonTerminal, ITokenCodes {

    public constant_part_select_range() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new constant_range(),
new constant_indexed_range()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public constant_part_select_range create() {
        return new constant_part_select_range();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


