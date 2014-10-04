
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class constraint_expression extends Acceptor implements NonTerminal, ITokenCodes {

    public constraint_expression() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new expression_or_dist(),
new Terminal(SEMI)),
new Sequence(new expression(),
new Terminal(MINUS_GT),
new constraint_set()),
new Sequence(new Terminal(IF_K),
new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN),
new constraint_set(),
new Optional(new Sequence(new Terminal(ELSE_K),
new constraint_set())) ),
new Sequence(new Terminal(FOREACH_K),
new Terminal(LPAREN),
new ps_or_hierarchical_array_identifier(),
new Terminal(LBRACK),
new loop_variables(),
new Terminal(RBRACK),
new Terminal(RPAREN),
new constraint_set())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public constraint_expression create() {
        return new constraint_expression();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


