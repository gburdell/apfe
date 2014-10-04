
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class unpacked_dimension extends Acceptor implements NonTerminal, ITokenCodes {

    public unpacked_dimension() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(LBRACK),
new constant_range(),
new Terminal(RBRACK)),
new Sequence(new Terminal(LBRACK),
new constant_expression(),
new Terminal(RBRACK))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public unpacked_dimension create() {
        return new unpacked_dimension();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


