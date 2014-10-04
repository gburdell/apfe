
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class clocking_direction extends Acceptor implements NonTerminal, ITokenCodes {

    public clocking_direction() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(INPUT_K),
new Optional(new clocking_skew()) ,
new Terminal(OUTPUT_K),
new Optional(new clocking_skew()) ),
new Sequence(new Terminal(INPUT_K),
new Optional(new clocking_skew()) ),
new Sequence(new Terminal(OUTPUT_K),
new Optional(new clocking_skew()) ),
new Terminal(INOUT_K)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public clocking_direction create() {
        return new clocking_direction();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


