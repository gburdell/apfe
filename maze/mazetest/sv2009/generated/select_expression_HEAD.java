
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class select_expression_HEAD extends Acceptor implements NonTerminal, ITokenCodes {

    public select_expression_HEAD() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new select_condition(),
new Sequence(new Terminal(NOT),
new select_condition()),
new Sequence(new Terminal(LPAREN),
new select_expression(),
new Terminal(RPAREN))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public select_expression_HEAD create() {
        return new select_expression_HEAD();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


