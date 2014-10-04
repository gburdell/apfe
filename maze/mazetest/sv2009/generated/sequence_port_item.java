
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class sequence_port_item extends Acceptor implements NonTerminal, ITokenCodes {

    public sequence_port_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Repetition(new attribute_instance()) ,
new Optional(new Sequence(new Terminal(LOCAL_K),
new Optional(new sequence_lvar_port_direction()) )) ,
new sequence_formal_type(),
new port_identifier(),
new Repetition(new variable_dimension()) ,
new Optional(new Sequence(new Terminal(EQ),
new sequence_actual_arg())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public sequence_port_item create() {
        return new sequence_port_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


