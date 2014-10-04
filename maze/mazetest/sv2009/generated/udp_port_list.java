
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class udp_port_list extends Acceptor implements NonTerminal, ITokenCodes {

    public udp_port_list() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new output_port_identifier(),
new Terminal(COMMA),
new input_port_identifier(),
new Repetition(new Sequence(new Terminal(COMMA),
new input_port_identifier())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public udp_port_list create() {
        return new udp_port_list();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


