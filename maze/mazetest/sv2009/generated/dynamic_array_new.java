
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class dynamic_array_new extends Acceptor implements NonTerminal, ITokenCodes {

    public dynamic_array_new() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(NEW_K),
new Optional(new expression()) ,
new Optional(new Sequence(new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN))) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public dynamic_array_new create() {
        return new dynamic_array_new();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


