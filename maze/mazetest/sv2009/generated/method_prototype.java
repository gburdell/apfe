
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class method_prototype extends Acceptor implements NonTerminal, ITokenCodes {

    public method_prototype() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new task_prototype(),
new function_prototype()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public method_prototype create() {
        return new method_prototype();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


