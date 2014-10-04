
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class module_or_generate_item extends Acceptor implements NonTerminal, ITokenCodes {

    public module_or_generate_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Repetition(new attribute_instance()) ,
new module_instantiation()),
new Sequence(new Repetition(new attribute_instance()) ,
new gate_instantiation()),
new Sequence(new Repetition(new attribute_instance()) ,
new udp_instantiation()),
new Sequence(new Repetition(new attribute_instance()) ,
new module_common_item()),
new Sequence(new Repetition(new attribute_instance()) ,
new parameter_override())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public module_or_generate_item create() {
        return new module_or_generate_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


