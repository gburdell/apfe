
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class property_expr_HEAD extends Acceptor implements NonTerminal, ITokenCodes {

    public property_expr_HEAD() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new sequence_expr(),
new Terminal(BAR_MINUS_GT),
new property_expr()),
new Sequence(new sequence_expr(),
new Terminal(BAR_EQ_GT),
new property_expr()),
new Sequence(new sequence_expr(),
new Terminal(POUND_MINUS_POUND),
new property_expr()),
new Sequence(new sequence_expr(),
new Terminal(POUND_EQ_POUND),
new property_expr()),
new sequence_expr(),
new Sequence(new Terminal(STRONG_K),
new Terminal(LPAREN),
new sequence_expr(),
new Terminal(RPAREN)),
new Sequence(new Terminal(WEAK_K),
new Terminal(LPAREN),
new sequence_expr(),
new Terminal(RPAREN)),
new Sequence(new Terminal(LPAREN),
new property_expr(),
new Terminal(RPAREN)),
new Sequence(new Terminal(NOT_K),
new property_expr()),
new Sequence(new Terminal(NEXTTIME_K),
new property_expr()),
new Sequence(new Terminal(NEXTTIME_K),
new Optional(new constant_expression()) ,
new property_expr()),
new Sequence(new Terminal(S_NEXTTIME_K),
new property_expr()),
new Sequence(new Terminal(S_NEXTTIME_K),
new Optional(new constant_expression()) ,
new property_expr()),
new Sequence(new Terminal(ALWAYS_K),
new property_expr()),
new Sequence(new Terminal(ALWAYS_K),
new Optional(new cycle_delay_const_range_expression()) ,
new property_expr()),
new Sequence(new Terminal(S_ALWAYS_K),
new Optional(new constant_range()) ,
new property_expr()),
new Sequence(new Terminal(S_EVENTUALLY_K),
new property_expr()),
new Sequence(new Terminal(EVENTUALLY_K),
new Optional(new constant_range()) ,
new property_expr()),
new Sequence(new Terminal(S_EVENTUALLY_K),
new Optional(new cycle_delay_const_range_expression()) ,
new property_expr()),
new Sequence(new Terminal(ACCEPT_ON_K),
new Terminal(LPAREN),
new expression_or_dist(),
new Terminal(RPAREN),
new property_expr()),
new Sequence(new Terminal(REJECT_ON_K),
new Terminal(LPAREN),
new expression_or_dist(),
new Terminal(RPAREN),
new property_expr()),
new Sequence(new Terminal(SYNC_ACCEPT_ON_K),
new Terminal(LPAREN),
new expression_or_dist(),
new Terminal(RPAREN),
new property_expr()),
new Sequence(new Terminal(SYNC_REJECT_ON_K),
new Terminal(LPAREN),
new expression_or_dist(),
new Terminal(RPAREN),
new property_expr()),
new property_instance(),
new Sequence(new clocking_event(),
new property_expr())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public property_expr_HEAD create() {
        return new property_expr_HEAD();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


