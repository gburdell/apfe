
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class trans_list extends Acceptor implements NonTerminal, ITokenCodes {

    public trans_list() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(LPAREN),
new trans_set(),
new Terminal(RPAREN),
new Repetition(new Sequence(new Terminal(COMMA),
new Terminal(LPAREN),
new trans_set(),
new Terminal(RPAREN))) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public trans_list create() {
        return new trans_list();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


