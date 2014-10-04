
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class cover_sequence_statement extends Acceptor implements NonTerminal, ITokenCodes {

    public cover_sequence_statement() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(COVER_K),
new Terminal(SEQUENCE_K),
new Terminal(LPAREN),
new Optional(new clocking_event()) ,
new Optional(new Sequence(new Terminal(DISABLE_K),
new Terminal(IFF_K),
new Terminal(LPAREN),
new expression_or_dist(),
new Terminal(RPAREN))) ,
new sequence_expr(),
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
    public cover_sequence_statement create() {
        return new cover_sequence_statement();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


