
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class case_generate_construct extends Acceptor implements NonTerminal, ITokenCodes {

    public case_generate_construct() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(CASE_K),
new Terminal(LPAREN),
new constant_expression(),
new Terminal(RPAREN),
new case_generate_item(),
new Repetition(new case_generate_item()) ,
new Terminal(ENDCASE_K)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public case_generate_construct create() {
        return new case_generate_construct();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


