
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class sequence_expr_HEAD extends Acceptor implements NonTerminal, ITokenCodes {

    public sequence_expr_HEAD() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new cycle_delay_range(),
new sequence_expr(),
new Repetition(new Sequence(new cycle_delay_range(),
new sequence_expr())) ),
new Sequence(new expression_or_dist(),
new Optional(new boolean_abbrev()) ),
new Sequence(new sequence_instance(),
new Optional(new sequence_abbrev()) ),
new Sequence(new Terminal(LPAREN),
new sequence_expr(),
new Repetition(new Sequence(new Terminal(COMMA),
new sequence_match_item())) ,
new Terminal(RPAREN),
new Optional(new sequence_abbrev()) ),
new Sequence(new Terminal(FIRST_MATCH_K),
new Terminal(LPAREN),
new sequence_expr(),
new Repetition(new Sequence(new Terminal(COMMA),
new sequence_match_item())) ,
new Terminal(RPAREN)),
new Sequence(new expression_or_dist(),
new Terminal(THROUGHOUT_K),
new sequence_expr()),
new Sequence(new clocking_event(),
new sequence_expr())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public sequence_expr_HEAD create() {
        return new sequence_expr_HEAD();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


