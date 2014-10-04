
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class clocking_drive extends Acceptor implements NonTerminal, ITokenCodes {

    public clocking_drive() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new clockvar_expression(),
new Terminal(LT_EQ),
new Optional(new cycle_delay()) ,
new expression()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public clocking_drive create() {
        return new clocking_drive();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


