
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class property_qualifier extends Acceptor implements NonTerminal, ITokenCodes {

    public property_qualifier() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new random_qualifier(),
new class_item_qualifier()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public property_qualifier create() {
        return new property_qualifier();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


