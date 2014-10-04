
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class non_port_module_item extends Acceptor implements NonTerminal, ITokenCodes {

    public non_port_module_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new generate_region(),
new module_or_generate_item(),
new specify_block(),
new Sequence(new Repetition(new attribute_instance()) ,
new specparam_declaration()),
new program_declaration(),
new module_declaration(),
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
    public non_port_module_item create() {
        return new non_port_module_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


