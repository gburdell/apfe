
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class cond_predicate extends Acceptor implements NonTerminal, ITokenCodes {

    public cond_predicate() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new expression_or_cond_pattern(),
new Repetition(new Sequence(new Terminal(AND3),
new expression_or_cond_pattern())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public cond_predicate create() {
        return new cond_predicate();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


