
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class function_body_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public function_body_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new function_data_type_or_implicit(),
new Optional(new Alternates(new Sequence(new interface_identifier(),
new Terminal(DOT)),
new class_scope())) ,
new function_identifier(),
new Terminal(LPAREN),
new Optional(new tf_port_list()) ,
new Terminal(RPAREN),
new Terminal(SEMI),
new Repetition(new block_item_declaration()) ,
new Repetition(new function_statement_or_null()) ,
new Terminal(ENDFUNCTION_K),
new Optional(new Sequence(new Terminal(COLON),
new function_identifier())) ),
new Sequence(new function_data_type_or_implicit(),
new Optional(new Alternates(new Sequence(new interface_identifier(),
new Terminal(DOT)),
new class_scope())) ,
new function_identifier(),
new Terminal(SEMI),
new Repetition(new tf_item_declaration()) ,
new Repetition(new function_statement_or_null()) ,
new Terminal(ENDFUNCTION_K),
new Optional(new Sequence(new Terminal(COLON),
new function_identifier())) )) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public function_body_declaration create() {
        return new function_body_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


