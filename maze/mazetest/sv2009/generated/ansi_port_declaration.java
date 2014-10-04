
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class ansi_port_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public ansi_port_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Optional(new port_direction()) ,
new port_identifier(),
new Repetition(new unpacked_dimension()) ,
new Optional(new Sequence(new Terminal(EQ),
new constant_expression())) ),
new Sequence(new Optional(new Alternates(new net_port_header(),
new interface_port_header())) ,
new port_identifier(),
new Repetition(new unpacked_dimension()) ,
new Optional(new Sequence(new Terminal(EQ),
new constant_expression())) ),
new Sequence(new Optional(new variable_port_header()) ,
new port_identifier(),
new Repetition(new variable_dimension()) ,
new Optional(new Sequence(new Terminal(EQ),
new constant_expression())) ),
new Sequence(new Optional(new port_direction()) ,
new Terminal(DOT),
new port_identifier(),
new Terminal(LPAREN),
new Optional(new expression()) ,
new Terminal(RPAREN))) ;
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


