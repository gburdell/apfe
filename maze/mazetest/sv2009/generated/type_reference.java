
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class type_reference extends Acceptor implements NonTerminal, ITokenCodes {

    public type_reference() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(TYPE_K),
new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN)),
new Sequence(new Terminal(TYPE_K),
new Terminal(LPAREN),
new data_type(),
new Terminal(RPAREN))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public type_reference create() {
        return new type_reference();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


