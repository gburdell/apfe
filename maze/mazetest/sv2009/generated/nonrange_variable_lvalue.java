
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class nonrange_variable_lvalue extends Acceptor implements NonTerminal, ITokenCodes {

    public nonrange_variable_lvalue() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Optional(new Alternates(new Sequence(new implicit_class_handle(),
new Terminal(DOT)),
new package_scope())) ,
new hierarchical_variable_identifier(),
new nonrange_select()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public nonrange_variable_lvalue create() {
        return new nonrange_variable_lvalue();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


