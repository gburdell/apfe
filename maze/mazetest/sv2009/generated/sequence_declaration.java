
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class sequence_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public sequence_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(SEQUENCE_K),
new sequence_identifier(),
new Optional(new Sequence(new Terminal(LPAREN),
new Optional(new sequence_port_list()) ,
new Terminal(RPAREN))) ,
new Terminal(SEMI),
new Repetition(new assertion_variable_declaration()) ,
new sequence_expr(),
new Terminal(SEMI),
new Terminal(ENDSEQUENCE_K),
new Optional(new Sequence(new Terminal(COLON),
new sequence_identifier())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public sequence_declaration create() {
        return new sequence_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


