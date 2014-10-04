
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class array_range_expression extends Acceptor implements NonTerminal, ITokenCodes {

    public array_range_expression() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new expression(),
new Terminal(COLON),
new expression()),
new Sequence(new expression(),
new Terminal(PLUS_COLON),
new expression()),
new Sequence(new expression(),
new Terminal(MINUS_COLON),
new expression()),
new expression()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public array_range_expression create() {
        return new array_range_expression();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


