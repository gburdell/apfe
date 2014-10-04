
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class interface_or_generate_item extends Acceptor implements NonTerminal, ITokenCodes {

    public interface_or_generate_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Repetition(new attribute_instance()) ,
new module_common_item()),
new Sequence(new Repetition(new attribute_instance()) ,
new modport_declaration()),
new Sequence(new Repetition(new attribute_instance()) ,
new extern_tf_declaration())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public interface_or_generate_item create() {
        return new interface_or_generate_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


