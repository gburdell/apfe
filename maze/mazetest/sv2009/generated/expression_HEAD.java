
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class expression_HEAD extends Acceptor implements NonTerminal, ITokenCodes {

    public expression_HEAD() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new unary_operator(),
new Repetition(new attribute_instance()) ,
new primary()),
new inc_or_dec_expression(),
new Sequence(new Terminal(LPAREN),
new operator_assignment(),
new Terminal(RPAREN)),
new tagged_union_expression(),
new primary()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public expression_HEAD create() {
        return new expression_HEAD();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


