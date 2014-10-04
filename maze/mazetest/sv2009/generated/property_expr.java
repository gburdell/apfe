
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class property_expr extends Acceptor implements NonTerminal, ITokenCodes {

    public property_expr() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new property_expr_HEAD(),
new Repetition(new property_expr_TAIL()) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public property_expr create() {
        return new property_expr();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


