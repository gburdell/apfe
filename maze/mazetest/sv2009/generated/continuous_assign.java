
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class continuous_assign extends Acceptor implements NonTerminal, ITokenCodes {

    public continuous_assign() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(ASSIGN_K),
new Optional(new drive_strength()) ,
new Optional(new delay3()) ,
new list_of_net_assignments(),
new Terminal(SEMI)),
new Sequence(new Terminal(ASSIGN_K),
new Optional(new delay_control()) ,
new list_of_variable_assignments(),
new Terminal(SEMI))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public continuous_assign create() {
        return new continuous_assign();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


