
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class genvar_initialization extends Acceptor implements NonTerminal, ITokenCodes {

    public genvar_initialization() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Optional(new Terminal(GENVAR_K)) ,
new genvar_identifier(),
new Terminal(EQ),
new constant_expression()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public genvar_initialization create() {
        return new genvar_initialization();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


