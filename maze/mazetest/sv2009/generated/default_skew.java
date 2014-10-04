
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class default_skew extends Acceptor implements NonTerminal, ITokenCodes {

    public default_skew() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(INPUT_K),
new clocking_skew(),
new Terminal(OUTPUT_K),
new clocking_skew()),
new Sequence(new Terminal(INPUT_K),
new clocking_skew()),
new Sequence(new Terminal(OUTPUT_K),
new clocking_skew())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public default_skew create() {
        return new default_skew();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


