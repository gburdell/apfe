
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class udp_body extends Acceptor implements NonTerminal, ITokenCodes {

    public udp_body() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new combinational_body(),
new sequential_body()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public udp_body create() {
        return new udp_body();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


