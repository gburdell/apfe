
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class pulse_control_specparam extends Acceptor implements NonTerminal, ITokenCodes {

    public pulse_control_specparam() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(PATHPULSE_K),
new Terminal(EQ),
new Terminal(LPAREN),
new reject_limit_value(),
new Optional(new Sequence(new Terminal(COMMA),
new error_limit_value())) ,
new Terminal(RPAREN)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public pulse_control_specparam create() {
        return new pulse_control_specparam();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


