
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class sequence_expr extends Acceptor implements NonTerminal, ITokenCodes {

    public sequence_expr() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new sequence_expr_HEAD(),
new Repetition(new sequence_expr_TAIL()) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public sequence_expr create() {
        return new sequence_expr();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


