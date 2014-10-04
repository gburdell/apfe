
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class charge_strength extends Acceptor implements NonTerminal, ITokenCodes {

    public charge_strength() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(LPAREN),
new Terminal(SMALL_K),
new Terminal(RPAREN)),
new Sequence(new Terminal(LPAREN),
new Terminal(MEDIUM_K),
new Terminal(RPAREN)),
new Sequence(new Terminal(LPAREN),
new Terminal(LARGE_K),
new Terminal(RPAREN))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public charge_strength create() {
        return new charge_strength();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


