
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class case_statement extends Acceptor implements NonTerminal, ITokenCodes {

    public case_statement() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Optional(new unique_priority()) ,
new case_keyword(),
new Terminal(LPAREN),
new case_expression(),
new Terminal(RPAREN),
new Terminal(MATCHES_K),
new case_pattern_item(),
new Repetition(new case_pattern_item()) ,
new Terminal(ENDCASE_K)),
new Sequence(new Optional(new unique_priority()) ,
new Terminal(CASE_K),
new Terminal(LPAREN),
new case_expression(),
new Terminal(RPAREN),
new Terminal(INSIDE_K),
new case_inside_item(),
new Repetition(new case_inside_item()) ,
new Terminal(ENDCASE_K)),
new Sequence(new Optional(new unique_priority()) ,
new case_keyword(),
new Terminal(LPAREN),
new case_expression(),
new Terminal(RPAREN),
new case_item(),
new Repetition(new case_item()) ,
new Terminal(ENDCASE_K))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public case_statement create() {
        return new case_statement();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


