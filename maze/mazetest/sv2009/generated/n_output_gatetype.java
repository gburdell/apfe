
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class n_output_gatetype extends Acceptor implements NonTerminal, ITokenCodes {

    public n_output_gatetype() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Terminal(BUF_K),
new Terminal(NOT_K)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public n_output_gatetype create() {
        return new n_output_gatetype();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


