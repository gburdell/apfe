
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class primary extends Acceptor implements NonTerminal, ITokenCodes {

    public primary() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new multiple_concatenation(),
new Optional(new Sequence(new Terminal(LBRACK),
new range_expression(),
new Terminal(RBRACK))) ),
new Sequence(new concatenation(),
new Optional(new Sequence(new Terminal(LBRACK),
new range_expression(),
new Terminal(RBRACK))) ),
new empty_queue(),
new subroutine_call(),
new let_expression(),
new Sequence(new Terminal(LPAREN),
new mintypmax_expression(),
new Terminal(RPAREN)),
new cast(),
new assignment_pattern_expression(),
new streaming_concatenation(),
new sequence_method_call(),
new Terminal(THIS_K),
new Terminal(DOLLAR),
new Terminal(NULL_K),
new Sequence(new Optional(new Alternates(new package_scope(),
new class_qualifier())) ,
new hierarchical_identifier(),
new select()),
new primary_literal()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public primary create() {
        return new primary();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


