
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class built_in_method_call extends Acceptor implements NonTerminal, ITokenCodes {

    public built_in_method_call() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new array_manipulation_call(),
new randomize_call()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public built_in_method_call create() {
        return new built_in_method_call();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


