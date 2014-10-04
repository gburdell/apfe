
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class bind_instantiation extends Acceptor implements NonTerminal, ITokenCodes {

    public bind_instantiation() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new program_instantiation(),
new module_instantiation(),
new interface_instantiation(),
new checker_instantiation()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public bind_instantiation create() {
        return new bind_instantiation();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


