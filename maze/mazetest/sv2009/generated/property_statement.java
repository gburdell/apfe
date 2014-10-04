
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class property_statement extends Acceptor implements NonTerminal, ITokenCodes {

    public property_statement() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(CASE_K),
new Terminal(LPAREN),
new expression_or_dist(),
new Terminal(RPAREN),
new property_case_item(),
new Repetition(new property_case_item()) ,
new Terminal(ENDCASE_K)),
new Sequence(new Terminal(IF_K),
new Terminal(LPAREN),
new expression_or_dist(),
new Terminal(RPAREN),
new property_expr(),
new Optional(new Sequence(new Terminal(ELSE_K),
new property_expr())) ),
new Sequence(new property_expr(),
new Terminal(SEMI))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public property_statement create() {
        return new property_statement();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


