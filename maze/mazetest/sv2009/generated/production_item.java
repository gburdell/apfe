
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class production_item extends Acceptor implements NonTerminal, ITokenCodes {

    public production_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new production_identifier(),
new Optional(new Sequence(new Terminal(LPAREN),
new list_of_arguments(),
new Terminal(RPAREN))) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public production_item create() {
        return new production_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


