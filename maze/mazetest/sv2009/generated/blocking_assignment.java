
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class blocking_assignment extends Acceptor implements NonTerminal, ITokenCodes {

    public blocking_assignment() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new variable_lvalue(),
new Terminal(EQ),
new delay_or_event_control(),
new expression()),
new Sequence(new Optional(new Alternates(new Sequence(new implicit_class_handle(),
new Terminal(DOT)),
new package_scope(),
new class_scope())) ,
new hierarchical_variable_identifier(),
new select(),
new Terminal(EQ),
new class_new()),
new Sequence(new nonrange_variable_lvalue(),
new Terminal(EQ),
new dynamic_array_new()),
new operator_assignment()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public blocking_assignment create() {
        return new blocking_assignment();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


