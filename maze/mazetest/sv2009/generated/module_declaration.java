
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class module_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public module_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new module_ansi_header(),
new Optional(new timeunits_declaration()) ,
new Repetition(new non_port_module_item()) ,
new Terminal(ENDMODULE_K),
new Optional(new Sequence(new Terminal(COLON),
new module_identifier())) ),
new Sequence(new module_nonansi_header(),
new Optional(new timeunits_declaration()) ,
new Repetition(new module_item()) ,
new Terminal(ENDMODULE_K),
new Optional(new Sequence(new Terminal(COLON),
new module_identifier())) ),
new Sequence(new Repetition(new attribute_instance()) ,
new module_keyword(),
new Optional(new lifetime()) ,
new module_identifier(),
new Terminal(DOT_STAR),
new Terminal(SEMI),
new Optional(new timeunits_declaration()) ,
new Repetition(new module_item()) ,
new Terminal(ENDMODULE_K),
new Optional(new Sequence(new Terminal(COLON),
new module_identifier())) ),
new Sequence(new Terminal(EXTERN_K),
new module_nonansi_header()),
new Sequence(new Terminal(EXTERN_K),
new module_ansi_header())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public module_declaration create() {
        return new module_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


