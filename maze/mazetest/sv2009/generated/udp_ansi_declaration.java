
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class udp_ansi_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public udp_ansi_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Repetition(new attribute_instance()) ,
new Terminal(PRIMITIVE_K),
new udp_identifier(),
new Terminal(LPAREN),
new udp_declaration_port_list(),
new Terminal(RPAREN),
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public udp_ansi_declaration create() {
        return new udp_ansi_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


