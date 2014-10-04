
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class variable_lvalue extends Acceptor implements NonTerminal, ITokenCodes {

    public variable_lvalue() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(LCURLY),
new variable_lvalue(),
new Repetition(new Sequence(new Terminal(COMMA),
new variable_lvalue())) ,
new Terminal(RCURLY)),
new Sequence(new Optional(new Alternates(new Sequence(new implicit_class_handle(),
new Terminal(DOT)),
new package_scope())) ,
new hierarchical_variable_identifier(),
new select()),
new Sequence(new Optional(new assignment_pattern_expression_type()) ,
new assignment_pattern_variable_lvalue()),
new streaming_concatenation()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public variable_lvalue create() {
        return new variable_lvalue();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


