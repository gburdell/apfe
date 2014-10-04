
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class var_data_type extends Acceptor implements NonTerminal, ITokenCodes {

    public var_data_type() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new data_type(),
new Sequence(new Terminal(VAR_K),
new data_type_or_implicit())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public var_data_type create() {
        return new var_data_type();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


