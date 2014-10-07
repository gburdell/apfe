
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class bit_select extends Acceptor implements NonTerminal, ITokenCodes {

    public bit_select() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Repetition(new Sequence(new Terminal(LBRACK),
new expression(),
new Terminal(RBRACK)))  ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public bit_select create() {
        return new bit_select();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}

