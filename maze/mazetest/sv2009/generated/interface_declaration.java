
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class interface_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public interface_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new interface_ansi_header(),
new Optional(new timeunits_declaration()) ,
new Repetition(new non_port_interface_item()) ,
new Terminal(ENDINTERFACE_K),
new Optional(new Sequence(new Terminal(COLON),
new interface_identifier())) ),
new Sequence(new interface_nonansi_header(),
new Optional(new timeunits_declaration()) ,
new Repetition(new interface_item()) ,
new Terminal(ENDINTERFACE_K),
new Optional(new Sequence(new Terminal(COLON),
new interface_identifier())) ),
new Sequence(new Repetition(new attribute_instance()) ,
new Terminal(INTERFACE_K),
new interface_identifier(),
new Terminal(DOT_STAR),
new Terminal(SEMI),
new Optional(new timeunits_declaration()) ,
new Repetition(new interface_item()) ,
new Terminal(ENDINTERFACE_K),
new Optional(new Sequence(new Terminal(COLON),
new interface_identifier())) ),
new Sequence(new Terminal(EXTERN_K),
new interface_ansi_header()),
new Sequence(new Terminal(EXTERN_K),
new interface_nonansi_header())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public interface_declaration create() {
        return new interface_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


