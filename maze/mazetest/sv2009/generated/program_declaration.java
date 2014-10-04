
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class program_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public program_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new program_ansi_header(),
new Optional(new timeunits_declaration()) ,
new Repetition(new non_port_program_item()) ,
new Terminal(ENDPROGRAM_K),
new Optional(new Sequence(new Terminal(COLON),
new program_identifier())) ),
new Sequence(new program_nonansi_header(),
new Optional(new timeunits_declaration()) ,
new Repetition(new program_item()) ,
new Terminal(ENDPROGRAM_K),
new Optional(new Sequence(new Terminal(COLON),
new program_identifier())) ),
new Sequence(new Repetition(new attribute_instance()) ,
new Terminal(PROGRAM_K),
new program_identifier(),
new Terminal(DOT_STAR),
new Terminal(SEMI),
new Optional(new timeunits_declaration()) ,
new Repetition(new program_item()) ,
new Terminal(ENDPROGRAM_K),
new Optional(new Sequence(new Terminal(COLON),
new program_identifier())) ),
new Sequence(new Terminal(EXTERN_K),
new program_ansi_header()),
new Sequence(new Terminal(EXTERN_K),
new program_nonansi_header())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public program_declaration create() {
        return new program_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


