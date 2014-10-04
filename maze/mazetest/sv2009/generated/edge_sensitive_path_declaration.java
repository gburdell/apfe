
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class edge_sensitive_path_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public edge_sensitive_path_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new parallel_edge_sensitive_path_description(),
new Terminal(EQ),
new path_delay_value()),
new Sequence(new full_edge_sensitive_path_description(),
new Terminal(EQ),
new path_delay_value())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public edge_sensitive_path_declaration create() {
        return new edge_sensitive_path_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


