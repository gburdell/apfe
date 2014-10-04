
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class udp_declaration_port_list extends Acceptor implements NonTerminal, ITokenCodes {

    public udp_declaration_port_list() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new udp_output_declaration(),
new Terminal(COMMA),
new udp_input_declaration(),
new Repetition(new Sequence(new Terminal(COMMA),
new udp_input_declaration())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public udp_declaration_port_list create() {
        return new udp_declaration_port_list();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


