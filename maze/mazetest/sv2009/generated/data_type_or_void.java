
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class data_type_or_void extends Acceptor implements NonTerminal, ITokenCodes {

    public data_type_or_void() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new data_type(),
new Terminal(VOID_K)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public data_type_or_void create() {
        return new data_type_or_void();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


