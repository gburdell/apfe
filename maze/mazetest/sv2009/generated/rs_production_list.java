
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class rs_production_list extends Acceptor implements NonTerminal, ITokenCodes {

    public rs_production_list() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(RAND_K),
new Terminal(JOIN_K),
new Optional(new Sequence(new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN))) ,
new production_item(),
new production_item(),
new Repetition(new production_item()) ),
new Sequence(new rs_prod(),
new Repetition(new rs_prod()) )) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public rs_production_list create() {
        return new rs_production_list();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


