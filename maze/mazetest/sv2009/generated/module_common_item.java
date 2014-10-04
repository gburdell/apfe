
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class module_common_item extends Acceptor implements NonTerminal, ITokenCodes {

    public module_common_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new initial_construct(),
new always_construct(),
new interface_instantiation(),
new program_instantiation(),
new assertion_item(),
new bind_directive(),
new continuous_assign(),
new net_alias(),
new final_construct(),
new loop_generate_construct(),
new conditional_generate_construct(),
new elaboration_system_task(),
new module_or_generate_item_declaration()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public module_common_item create() {
        return new module_common_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


