
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class expression_or_cond_pattern extends Acceptor implements NonTerminal, ITokenCodes {

    public expression_or_cond_pattern() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new expression(),
new Optional(new Sequence(new Terminal(MATCHES_K),
new pattern())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public expression_or_cond_pattern create() {
        return new expression_or_cond_pattern();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


