
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class hierarchical_instance extends Acceptor implements NonTerminal, ITokenCodes {

    public hierarchical_instance() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new name_of_instance(),
new Terminal(LPAREN),
new Optional(new list_of_port_connections()) ,
new Terminal(RPAREN)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public hierarchical_instance create() {
        return new hierarchical_instance();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


