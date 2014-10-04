
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class parallel_path_description extends Acceptor implements NonTerminal, ITokenCodes {

    public parallel_path_description() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(LPAREN),
new specify_input_terminal_descriptor(),
new Optional(new polarity_operator()) ,
new Terminal(EQ_GT),
new specify_output_terminal_descriptor(),
new Terminal(RPAREN)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public parallel_path_description create() {
        return new parallel_path_description();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


