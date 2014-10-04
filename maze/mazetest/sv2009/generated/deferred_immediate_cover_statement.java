
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class deferred_immediate_cover_statement extends Acceptor implements NonTerminal, ITokenCodes {

    public deferred_immediate_cover_statement() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(COVER_K),
new Terminal(POUND),
new Terminal(UNSIGNED_NUMBER),
new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN),
new statement_or_null()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public deferred_immediate_cover_statement create() {
        return new deferred_immediate_cover_statement();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


