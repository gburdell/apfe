
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class n_input_gatetype extends Acceptor implements NonTerminal, ITokenCodes {

    public n_input_gatetype() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Terminal(AND_K),
new Terminal(NAND_K),
new Terminal(OR_K),
new Terminal(NOR_K),
new Terminal(XOR_K),
new Terminal(XNOR_K)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public n_input_gatetype create() {
        return new n_input_gatetype();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


