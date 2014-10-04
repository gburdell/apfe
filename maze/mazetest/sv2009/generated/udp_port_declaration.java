
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class udp_port_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public udp_port_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new udp_output_declaration(),
new Terminal(SEMI)),
new Sequence(new udp_input_declaration(),
new Terminal(SEMI)),
new Sequence(new udp_reg_declaration(),
new Terminal(SEMI))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public udp_port_declaration create() {
        return new udp_port_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


