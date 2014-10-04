
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class seq_input_list extends Acceptor implements NonTerminal, ITokenCodes {

    public seq_input_list() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new level_input_list(),
new edge_input_list()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public seq_input_list create() {
        return new seq_input_list();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


