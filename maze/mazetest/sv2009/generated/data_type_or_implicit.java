
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class data_type_or_implicit extends Acceptor implements NonTerminal, ITokenCodes {

    public data_type_or_implicit() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new data_type(),
new implicit_data_type()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public data_type_or_implicit create() {
        return new data_type_or_implicit();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}

