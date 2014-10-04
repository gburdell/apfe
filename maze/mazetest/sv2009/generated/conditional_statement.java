
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class conditional_statement extends Acceptor implements NonTerminal, ITokenCodes {

    public conditional_statement() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Optional(new unique_priority()) ,
new Terminal(IF_K),
new Terminal(LPAREN),
new cond_predicate(),
new Terminal(RPAREN),
new statement_or_null(),
new Repetition(new Sequence(new Terminal(ELSE_K),
new Terminal(IF_K),
new Terminal(LPAREN),
new cond_predicate(),
new Terminal(RPAREN),
new statement_or_null())) ,
new Optional(new Sequence(new Terminal(ELSE_K),
new statement_or_null())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public conditional_statement create() {
        return new conditional_statement();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


