
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class parameter_port_list extends Acceptor implements NonTerminal, ITokenCodes {

    public parameter_port_list() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(POUND),
new Terminal(LPAREN),
new list_of_param_assignments(),
new Repetition(new Sequence(new Terminal(COMMA),
new parameter_port_declaration())) ,
new Terminal(RPAREN)),
new Sequence(new Terminal(POUND),
new Terminal(LPAREN),
new parameter_port_declaration(),
new Repetition(new Sequence(new Terminal(COMMA),
new parameter_port_declaration())) ,
new Terminal(RPAREN)),
new Sequence(new Terminal(POUND),
new Terminal(LPAREN),
new Terminal(RPAREN))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public parameter_port_list create() {
        return new parameter_port_list();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


