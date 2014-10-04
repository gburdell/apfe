
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class bins_selection extends Acceptor implements NonTerminal, ITokenCodes {

    public bins_selection() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new bins_keyword(),
new bin_identifier(),
new Terminal(EQ),
new select_expression(),
new Optional(new Sequence(new Terminal(IFF_K),
new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN))) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public bins_selection create() {
        return new bins_selection();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


