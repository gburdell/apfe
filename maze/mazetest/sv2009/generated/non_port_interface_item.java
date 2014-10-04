
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class non_port_interface_item extends Acceptor implements NonTerminal, ITokenCodes {

    public non_port_interface_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new generate_region(),
new interface_or_generate_item(),
new program_declaration(),
new interface_declaration(),
new timeunits_declaration()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public non_port_interface_item create() {
        return new non_port_interface_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


