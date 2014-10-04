
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class input_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public input_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(INPUT_K),
new list_of_port_identifiers()),
new Sequence(new Terminal(INPUT_K),
new net_type(),
new list_of_port_identifiers()),
new Sequence(new Terminal(INPUT_K),
new net_port_type(),
new list_of_port_identifiers()),
new Sequence(new Terminal(INPUT_K),
new variable_port_type(),
new list_of_variable_identifiers())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public input_declaration create() {
        return new input_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


