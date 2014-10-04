
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class cover_cross extends Acceptor implements NonTerminal, ITokenCodes {

    public cover_cross() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Optional(new Sequence(new cross_identifier(),
new Terminal(COLON))) ,
new Terminal(CROSS_K),
new list_of_coverpoints(),
new Optional(new Sequence(new Terminal(IFF_K),
new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN))) ,
new select_bins_or_empty()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public cover_cross create() {
        return new cover_cross();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


