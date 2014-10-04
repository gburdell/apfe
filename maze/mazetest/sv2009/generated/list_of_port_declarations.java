
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class list_of_port_declarations extends Acceptor implements NonTerminal, ITokenCodes {

    public list_of_port_declarations() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(LPAREN),
new Optional(new Sequence(new Repetition(new attribute_instance()) ,
new ansi_port_declaration(),
new Repetition(new Sequence(new Terminal(COMMA),
new Repetition(new attribute_instance()) ,
new ansi_port_declaration())) )) ,
new Terminal(RPAREN)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public list_of_port_declarations create() {
        return new list_of_port_declarations();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


