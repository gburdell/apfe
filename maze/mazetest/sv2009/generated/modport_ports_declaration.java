
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class modport_ports_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public modport_ports_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Repetition(new attribute_instance()) ,
new modport_simple_ports_declaration()),
new Sequence(new Repetition(new attribute_instance()) ,
new modport_tf_ports_declaration()),
new Sequence(new Repetition(new attribute_instance()) ,
new modport_clocking_declaration())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public modport_ports_declaration create() {
        return new modport_ports_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


