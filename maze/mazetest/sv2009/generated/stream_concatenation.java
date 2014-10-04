
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class stream_concatenation extends Acceptor implements NonTerminal, ITokenCodes {

    public stream_concatenation() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(LCURLY),
new stream_expression(),
new Repetition(new Sequence(new Terminal(COMMA),
new stream_expression())) ,
new Terminal(RCURLY)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public stream_concatenation create() {
        return new stream_concatenation();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


