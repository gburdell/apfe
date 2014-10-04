
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class rs_if_else extends Acceptor implements NonTerminal, ITokenCodes {

    public rs_if_else() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(IF_K),
new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN),
new production_item(),
new Optional(new Sequence(new Terminal(ELSE_K),
new production_item())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public rs_if_else create() {
        return new rs_if_else();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


