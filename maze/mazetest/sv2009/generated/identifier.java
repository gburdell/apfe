
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class identifier extends Acceptor implements NonTerminal, ITokenCodes {

    public identifier() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new simple_identifier(),
new escaped_identifier()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public identifier create() {
        return new identifier();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


