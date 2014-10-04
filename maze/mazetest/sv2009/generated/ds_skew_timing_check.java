
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class ds_skew_timing_check extends Acceptor implements NonTerminal, ITokenCodes {

    public ds_skew_timing_check() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(DS_SKEW_K),
new Terminal(LPAREN),
new reference_event(),
new Terminal(COMMA),
new data_event(),
new Terminal(COMMA),
new timing_check_limit(),
new Optional(new Sequence(new Terminal(COMMA),
new Optional(new notifier()) )) ,
new Terminal(RPAREN),
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public ds_skew_timing_check create() {
        return new ds_skew_timing_check();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


