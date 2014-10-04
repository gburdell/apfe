
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class delayed_data extends Acceptor implements NonTerminal, ITokenCodes {

    public delayed_data() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new terminal_identifier(),
new Optional(new Sequence(new Terminal(LBRACK),
new constant_mintypmax_expression(),
new Terminal(RBRACK))) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public delayed_data create() {
        return new delayed_data();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


