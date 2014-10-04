
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class udp_output_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public udp_output_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Repetition(new attribute_instance()) ,
new Terminal(OUTPUT_K),
new Terminal(REG_K),
new port_identifier(),
new Optional(new Sequence(new Terminal(EQ),
new constant_expression())) ),
new Sequence(new Repetition(new attribute_instance()) ,
new Terminal(OUTPUT_K),
new port_identifier())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public udp_output_declaration create() {
        return new udp_output_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


