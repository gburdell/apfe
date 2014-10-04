
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class select_expression extends Acceptor implements NonTerminal, ITokenCodes {

    public select_expression() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new select_expression_HEAD(),
new Repetition(new select_expression_TAIL()) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public select_expression create() {
        return new select_expression();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


