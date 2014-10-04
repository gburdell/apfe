
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class constant_expression_TAIL extends Acceptor implements NonTerminal, ITokenCodes {

    public constant_expression_TAIL() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(QMARK),
new Repetition(new attribute_instance()) ,
new constant_expression(),
new Terminal(COLON),
new constant_expression()),
new Sequence(new binary_operator(),
new Repetition(new attribute_instance()) ,
new constant_expression())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public constant_expression_TAIL create() {
        return new constant_expression_TAIL();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


