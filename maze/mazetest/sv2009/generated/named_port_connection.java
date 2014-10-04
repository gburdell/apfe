
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class named_port_connection extends Acceptor implements NonTerminal, ITokenCodes {

    public named_port_connection() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Repetition(new attribute_instance()) ,
new Terminal(DOT),
new port_identifier(),
new Optional(new Sequence(new Terminal(LPAREN),
new Optional(new expression()) ,
new Terminal(RPAREN))) ),
new Sequence(new Repetition(new attribute_instance()) ,
new Terminal(DOT_STAR))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public named_port_connection create() {
        return new named_port_connection();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


