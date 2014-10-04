
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class coverage_event extends Acceptor implements NonTerminal, ITokenCodes {

    public coverage_event() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(WITH_K),
new Terminal(FUNCTION_K),
new Terminal(IDENT),
new Terminal(LPAREN),
new Optional(new tf_port_list()) ,
new Terminal(RPAREN)),
new Sequence(new Terminal(AT2),
new Terminal(LPAREN),
new block_event_expression(),
new Terminal(RPAREN)),
new clocking_event()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public coverage_event create() {
        return new coverage_event();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


