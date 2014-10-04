
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class modport_tf_ports_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public modport_tf_ports_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new import_export(),
new modport_tf_port(),
new Repetition(new Sequence(new Terminal(COMMA),
new modport_tf_port())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public modport_tf_ports_declaration create() {
        return new modport_tf_ports_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


