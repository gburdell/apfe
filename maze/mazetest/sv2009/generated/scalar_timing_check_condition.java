
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class scalar_timing_check_condition extends Acceptor implements NonTerminal, ITokenCodes {

    public scalar_timing_check_condition() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new expression(),
new Terminal(EQ2),
new scalar_constant()),
new Sequence(new expression(),
new Terminal(EQ3),
new scalar_constant()),
new Sequence(new expression(),
new Terminal(NOT_EQ),
new scalar_constant()),
new Sequence(new expression(),
new Terminal(NOT_EQ2),
new scalar_constant()),
new Sequence(new Optional(new Terminal(TILDE)) ,
new expression())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public scalar_timing_check_condition create() {
        return new scalar_timing_check_condition();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


