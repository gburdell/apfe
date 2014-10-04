
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class variable_port_type extends Acceptor implements NonTerminal, ITokenCodes {

    public variable_port_type() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new var_data_type() ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public variable_port_type create() {
        return new variable_port_type();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


