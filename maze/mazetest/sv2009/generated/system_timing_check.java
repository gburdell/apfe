
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class system_timing_check extends Acceptor implements NonTerminal, ITokenCodes {

    public system_timing_check() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new ds_setup_timing_check(),
new ds_hold_timing_check(),
new ds_setuphold_timing_check(),
new ds_recovery_timing_check(),
new ds_removal_timing_check(),
new ds_recrem_timing_check(),
new ds_skew_timing_check(),
new ds_timeskew_timing_check(),
new ds_fullskew_timing_check(),
new ds_period_timing_check(),
new ds_width_timing_check(),
new ds_nochange_timing_check()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public system_timing_check create() {
        return new system_timing_check();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


