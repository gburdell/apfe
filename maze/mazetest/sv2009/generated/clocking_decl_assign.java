
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class clocking_decl_assign extends Acceptor implements NonTerminal, ITokenCodes {

    public clocking_decl_assign() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new signal_identifier(),
new Optional(new Sequence(new Terminal(EQ),
new expression())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public clocking_decl_assign create() {
        return new clocking_decl_assign();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


