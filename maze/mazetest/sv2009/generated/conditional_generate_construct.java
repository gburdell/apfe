
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class conditional_generate_construct extends Acceptor implements NonTerminal, ITokenCodes {

    public conditional_generate_construct() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new if_generate_construct(),
new case_generate_construct()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public conditional_generate_construct create() {
        return new conditional_generate_construct();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


