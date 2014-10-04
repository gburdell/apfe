
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class edge_control_specifier extends Acceptor implements NonTerminal, ITokenCodes {

    public edge_control_specifier() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(EDGE_K),
new Terminal(LBRACK),
new edge_descriptor(),
new Repetition(new Sequence(new Terminal(COMMA),
new edge_descriptor())) ,
new Terminal(RBRACK)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public edge_control_specifier create() {
        return new edge_control_specifier();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


