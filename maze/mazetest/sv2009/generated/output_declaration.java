
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class output_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public output_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(OUTPUT_K),
new list_of_port_identifiers()),
new Sequence(new Terminal(OUTPUT_K),
new net_type(),
new list_of_port_identifiers()),
new Sequence(new Terminal(OUTPUT_K),
new net_port_type(),
new list_of_port_identifiers()),
new Sequence(new Terminal(OUTPUT_K),
new variable_port_type(),
new list_of_variable_port_identifiers())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public output_declaration create() {
        return new output_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


