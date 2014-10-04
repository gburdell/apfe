
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class coverage_spec extends Acceptor implements NonTerminal, ITokenCodes {

    public coverage_spec() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new cover_point(),
new cover_cross()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public coverage_spec create() {
        return new coverage_spec();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


