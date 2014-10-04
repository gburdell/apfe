
package apfe.maze.tc1.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.tc1.*;



public  class ansi_port_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public ansi_port_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Optional(new port_direction()) ,
new port_identifier(),
new Repetition(new unpacked_dimension()) ,
new Optional(new Sequence(new Terminal(EQ),
new Terminal(NUMBER))) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public ansi_port_declaration create() {
        return new ansi_port_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


