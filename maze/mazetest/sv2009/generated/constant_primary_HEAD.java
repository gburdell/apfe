
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class constant_primary_HEAD extends Acceptor implements NonTerminal, ITokenCodes {

    public constant_primary_HEAD() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Alternates(new simple_type(),
new signing(),
new Terminal(STRING_K),
new Terminal(CONST_K)),
new Terminal(SQUOTE),
new Terminal(LPAREN),
new constant_expression(),
new Terminal(RPAREN)),
new Sequence(new constant_multiple_concatenation(),
new Optional(new Sequence(new Terminal(LBRACK),
new constant_range_expression(),
new Terminal(RBRACK))) ),
new Sequence(new constant_concatenation(),
new Optional(new Sequence(new Terminal(LBRACK),
new constant_range_expression(),
new Terminal(RBRACK))) ),
new constant_let_expression(),
new Sequence(new Terminal(LPAREN),
new constant_mintypmax_expression(),
new Terminal(RPAREN)),
new subroutine_call(),
new Sequence(new specparam_identifier(),
new Optional(new Sequence(new Terminal(LBRACK),
new constant_range_expression(),
new Terminal(RBRACK))) ),
new Sequence(new ps_parameter_identifier(),
new constant_select()),
new genvar_identifier(),
new Sequence(new Optional(new Alternates(new package_scope(),
new class_scope())) ,
new enum_identifier()),
new constant_assignment_pattern_expression(),
new type_reference(),
new primary_literal()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public constant_primary_HEAD create() {
        return new constant_primary_HEAD();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


