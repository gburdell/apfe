
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class genvar_iteration extends Acceptor implements NonTerminal, ITokenCodes {

    public genvar_iteration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new genvar_identifier(),
new assignment_operator(),
new genvar_expression()),
new Sequence(new inc_or_dec_operator(),
new genvar_identifier()),
new Sequence(new genvar_identifier(),
new inc_or_dec_operator())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public genvar_iteration create() {
        return new genvar_iteration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


