
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class port_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public port_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Repetition(new attribute_instance()) ,
new interface_port_declaration()),
new Sequence(new Repetition(new attribute_instance()) ,
new inout_declaration()),
new Sequence(new Repetition(new attribute_instance()) ,
new input_declaration()),
new Sequence(new Repetition(new attribute_instance()) ,
new output_declaration()),
new Sequence(new Repetition(new attribute_instance()) ,
new ref_declaration())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public port_declaration create() {
        return new port_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


