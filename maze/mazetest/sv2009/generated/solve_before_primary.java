
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class solve_before_primary extends Acceptor implements NonTerminal, ITokenCodes {

    public solve_before_primary() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Optional(new Alternates(new Sequence(new implicit_class_handle(),
new Terminal(DOT)),
new class_scope())) ,
new hierarchical_identifier(),
new select()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public solve_before_primary create() {
        return new solve_before_primary();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


