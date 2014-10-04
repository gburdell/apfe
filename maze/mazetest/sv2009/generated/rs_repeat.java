
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class rs_repeat extends Acceptor implements NonTerminal, ITokenCodes {

    public rs_repeat() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(REPEAT_K),
new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN),
new production_item()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public rs_repeat create() {
        return new rs_repeat();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


