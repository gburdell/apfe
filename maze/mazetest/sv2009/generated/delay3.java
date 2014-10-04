
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class delay3 extends Acceptor implements NonTerminal, ITokenCodes {

    public delay3() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(POUND),
new Terminal(LPAREN),
new mintypmax_expression(),
new Optional(new Sequence(new Terminal(COMMA),
new mintypmax_expression(),
new Optional(new Sequence(new Terminal(COMMA),
new mintypmax_expression())) )) ,
new Terminal(RPAREN)),
new Sequence(new Terminal(POUND),
new delay_value())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public delay3 create() {
        return new delay3();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


