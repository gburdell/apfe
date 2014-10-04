
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class cycle_delay extends Acceptor implements NonTerminal, ITokenCodes {

    public cycle_delay() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(POUND2),
new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN)),
new Sequence(new Terminal(POUND2),
new Terminal(NUMBER)),
new Sequence(new Terminal(POUND2),
new identifier())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public cycle_delay create() {
        return new cycle_delay();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


