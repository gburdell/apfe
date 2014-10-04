
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class rs_case extends Acceptor implements NonTerminal, ITokenCodes {

    public rs_case() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(CASE_K),
new Terminal(LPAREN),
new case_expression(),
new Terminal(RPAREN),
new rs_case_item(),
new Repetition(new rs_case_item()) ,
new Terminal(ENDCASE_K)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public rs_case create() {
        return new rs_case();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


