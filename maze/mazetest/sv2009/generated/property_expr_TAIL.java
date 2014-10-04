
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class property_expr_TAIL extends Acceptor implements NonTerminal, ITokenCodes {

    public property_expr_TAIL() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(OR_K),
new property_expr()),
new Sequence(new Terminal(UNTIL_K),
new property_expr()),
new Sequence(new Terminal(S_UNTIL_K),
new property_expr()),
new Sequence(new Terminal(UNTIL_WITH_K),
new property_expr()),
new Sequence(new Terminal(S_UNTIL_WITH_K),
new property_expr()),
new Sequence(new Terminal(IMPLIES_K),
new property_expr()),
new Sequence(new Terminal(IFF_K),
new property_expr()),
new Sequence(new Terminal(AND_K),
new property_expr())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public property_expr_TAIL create() {
        return new property_expr_TAIL();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


