
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class non_port_program_item extends Acceptor implements NonTerminal, ITokenCodes {

    public non_port_program_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Repetition(new attribute_instance()) ,
new continuous_assign()),
new Sequence(new Repetition(new attribute_instance()) ,
new module_or_generate_item_declaration()),
new Sequence(new Repetition(new attribute_instance()) ,
new initial_construct()),
new Sequence(new Repetition(new attribute_instance()) ,
new final_construct()),
new Sequence(new Repetition(new attribute_instance()) ,
new concurrent_assertion_item()),
new Sequence(new Repetition(new attribute_instance()) ,
new timeunits_declaration()),
new program_generate_item()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public non_port_program_item create() {
        return new non_port_program_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}

