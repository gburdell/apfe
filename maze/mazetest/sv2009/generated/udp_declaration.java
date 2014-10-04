
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class udp_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public udp_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new udp_ansi_declaration(),
new udp_body(),
new Terminal(ENDPRIMITIVE_K),
new Optional(new Sequence(new Terminal(COLON),
new udp_identifier())) ),
new Sequence(new udp_nonansi_declaration(),
new udp_port_declaration(),
new Repetition(new udp_port_declaration()) ,
new udp_body(),
new Terminal(ENDPRIMITIVE_K),
new Optional(new Sequence(new Terminal(COLON),
new udp_identifier())) ),
new Sequence(new Terminal(EXTERN_K),
new udp_ansi_declaration()),
new Sequence(new Terminal(EXTERN_K),
new udp_nonansi_declaration()),
new Sequence(new Repetition(new attribute_instance()) ,
new Terminal(PRIMITIVE_K),
new udp_identifier(),
new Terminal(LPAREN),
new Terminal(DOT_STAR),
new Terminal(RPAREN),
new Terminal(SEMI),
new Repetition(new udp_port_declaration()) ,
new udp_body(),
new Terminal(ENDPRIMITIVE_K),
new Optional(new Sequence(new Terminal(COLON),
new udp_identifier())) )) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public udp_declaration create() {
        return new udp_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


