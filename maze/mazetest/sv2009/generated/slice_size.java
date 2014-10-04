
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class slice_size extends Acceptor implements NonTerminal, ITokenCodes {

    public slice_size() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new simple_type(),
new constant_expression()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public slice_size create() {
        return new slice_size();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


