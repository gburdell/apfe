
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class sequence_actual_arg extends Acceptor implements NonTerminal, ITokenCodes {

    public sequence_actual_arg() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new event_expression(),
new sequence_expr()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public sequence_actual_arg create() {
        return new sequence_actual_arg();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


