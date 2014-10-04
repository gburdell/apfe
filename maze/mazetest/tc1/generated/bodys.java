
package apfe.maze.tc1.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.tc1.*;



public  class bodys extends Acceptor implements NonTerminal, ITokenCodes {

    public bodys() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Repetition(new body())  ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public bodys create() {
        return new bodys();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


