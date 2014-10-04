
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class stream_expression extends Acceptor implements NonTerminal, ITokenCodes {

    public stream_expression() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new expression(),
new Optional(new Sequence(new Terminal(WITH_K),
new Terminal(LBRACK),
new array_range_expression(),
new Terminal(RBRACK))) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public stream_expression create() {
        return new stream_expression();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


