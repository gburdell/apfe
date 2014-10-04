
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class inc_or_dec_expression extends Acceptor implements NonTerminal, ITokenCodes {

    public inc_or_dec_expression() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new inc_or_dec_operator(),
new Repetition(new attribute_instance()) ,
new variable_lvalue()),
new Sequence(new variable_lvalue(),
new Repetition(new attribute_instance()) ,
new inc_or_dec_operator())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public inc_or_dec_expression create() {
        return new inc_or_dec_expression();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


