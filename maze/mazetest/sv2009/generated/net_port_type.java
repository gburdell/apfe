
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class net_port_type extends Acceptor implements NonTerminal, ITokenCodes {

    public net_port_type() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Optional(new net_type()) ,
new data_type_or_implicit()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public net_port_type create() {
        return new net_port_type();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}

