
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class loop_statement extends Acceptor implements NonTerminal, ITokenCodes {

    public loop_statement() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(FOREVER_K),
new statement_or_null()),
new Sequence(new Terminal(REPEAT_K),
new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN),
new statement_or_null()),
new Sequence(new Terminal(WHILE_K),
new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN),
new statement_or_null()),
new Sequence(new Terminal(FOR_K),
new Terminal(LPAREN),
new for_initialization(),
new Terminal(SEMI),
new expression(),
new Terminal(SEMI),
new for_step(),
new Terminal(RPAREN),
new statement_or_null()),
new Sequence(new Terminal(DO_K),
new statement_or_null(),
new Terminal(WHILE_K),
new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN),
new Terminal(SEMI)),
new Sequence(new Terminal(FOREACH_K),
new Terminal(LPAREN),
new ps_or_hierarchical_array_identifier(),
new Terminal(LBRACK),
new loop_variables(),
new Terminal(RBRACK),
new Terminal(RPAREN),
new statement())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public loop_statement create() {
        return new loop_statement();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


