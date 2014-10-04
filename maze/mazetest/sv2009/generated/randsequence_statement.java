
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class randsequence_statement extends Acceptor implements NonTerminal, ITokenCodes {

    public randsequence_statement() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(RANDSEQUENCE_K),
new Terminal(LPAREN),
new Optional(new production_identifier()) ,
new Terminal(RPAREN),
new production(),
new Repetition(new production()) ,
new Terminal(ENDSEQUENCE_K)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public randsequence_statement create() {
        return new randsequence_statement();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


