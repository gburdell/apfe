
package apfe.maze.tc2.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.tc2.*;



public  class d extends Acceptor implements NonTerminal, ITokenCodes {

    public d() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Repetition(new e())  ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public d create() {
        return new d();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


