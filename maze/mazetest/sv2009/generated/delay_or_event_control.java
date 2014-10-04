
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class delay_or_event_control extends Acceptor implements NonTerminal, ITokenCodes {

    public delay_or_event_control() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(REPEAT_K),
new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN),
new event_control()),
new delay_control(),
new event_control()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public delay_or_event_control create() {
        return new delay_or_event_control();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


