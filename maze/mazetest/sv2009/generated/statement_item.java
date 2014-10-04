
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class statement_item extends Acceptor implements NonTerminal, ITokenCodes {

    public statement_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new blocking_assignment(),
new Terminal(SEMI)),
new Sequence(new nonblocking_assignment(),
new Terminal(SEMI)),
new Sequence(new procedural_continuous_assignment(),
new Terminal(SEMI)),
new case_statement(),
new conditional_statement(),
new Sequence(new inc_or_dec_expression(),
new Terminal(SEMI)),
new subroutine_call_statement(),
new disable_statement(),
new event_trigger(),
new loop_statement(),
new jump_statement(),
new par_block(),
new procedural_timing_control_statement(),
new seq_block(),
new wait_statement(),
new procedural_assertion_statement(),
new Sequence(new clocking_drive(),
new Terminal(SEMI)),
new randsequence_statement(),
new randcase_statement(),
new expect_property_statement()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public statement_item create() {
        return new statement_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


