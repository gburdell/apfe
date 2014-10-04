
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class property_spec extends Acceptor implements NonTerminal, ITokenCodes {

    public property_spec() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Optional(new clocking_event()) ,
new Optional(new Sequence(new Terminal(DISABLE_K),
new Terminal(IFF_K),
new Terminal(LPAREN),
new expression_or_dist(),
new Terminal(RPAREN))) ,
new property_expr()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public property_spec create() {
        return new property_spec();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


