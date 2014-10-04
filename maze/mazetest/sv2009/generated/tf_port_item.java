
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class tf_port_item extends Acceptor implements NonTerminal, ITokenCodes {

    public tf_port_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Repetition(new attribute_instance()) ,
new Optional(new tf_port_direction()) ,
new Optional(new Terminal(VAR_K)) ,
new data_type_or_implicit(),
new Optional(new Sequence(new port_identifier(),
new Repetition(new variable_dimension()) ,
new Optional(new Sequence(new Terminal(EQ),
new expression())) )) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public tf_port_item create() {
        return new tf_port_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


