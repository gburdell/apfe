
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class net_lvalue extends Acceptor implements NonTerminal, ITokenCodes {

    public net_lvalue() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(LCURLY),
new net_lvalue(),
new Repetition(new Sequence(new Terminal(COMMA),
new net_lvalue())) ,
new Terminal(RCURLY)),
new Sequence(new ps_or_hierarchical_net_identifier(),
new constant_select()),
new Sequence(new Optional(new assignment_pattern_expression_type()) ,
new assignment_pattern_net_lvalue())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public net_lvalue create() {
        return new net_lvalue();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


