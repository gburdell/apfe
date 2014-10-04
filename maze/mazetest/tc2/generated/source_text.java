
package apfe.maze.tc2.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.tc2.*;



public  class source_text extends Acceptor implements NonTerminal, ITokenCodes {

    public source_text() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new a(),
new b(),
new c(),
new d(),
new EOF()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public source_text create() {
        return new source_text();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


