
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class loop_generate_construct extends Acceptor implements NonTerminal, ITokenCodes {

    public loop_generate_construct() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(FOR_K),
new Terminal(LPAREN),
new genvar_initialization(),
new Terminal(SEMI),
new genvar_expression(),
new Terminal(SEMI),
new genvar_iteration(),
new Terminal(RPAREN),
new generate_block()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public loop_generate_construct create() {
        return new loop_generate_construct();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


