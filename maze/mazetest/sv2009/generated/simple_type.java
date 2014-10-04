
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class simple_type extends Acceptor implements NonTerminal, ITokenCodes {

    public simple_type() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new integer_type(),
new non_integer_type(),
new ps_type_identifier(),
new ps_parameter_identifier()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public simple_type create() {
        return new simple_type();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


