
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class property_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public property_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(PROPERTY_K),
new property_identifier(),
new Optional(new Sequence(new Terminal(LPAREN),
new Optional(new property_port_list()) ,
new Terminal(RPAREN))) ,
new Terminal(SEMI),
new Repetition(new assertion_variable_declaration()) ,
new property_statement_spec(),
new Terminal(ENDPROPERTY_K),
new Optional(new Sequence(new Terminal(COLON),
new property_identifier())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public property_declaration create() {
        return new property_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


