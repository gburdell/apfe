
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class gate_instantiation extends Acceptor implements NonTerminal, ITokenCodes {

    public gate_instantiation() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Alternates(new cmos_switchtype(),
new enable_gatetype(),
new mos_switchtype(),
new n_input_gatetype(),
new n_output_gatetype(),
new pass_en_switchtype(),
new pass_switchtype(),
new Terminal(PULLDOWN_K),
new Terminal(PULLUP_K)),
new Optional(new drive_strength()) ,
new Optional(new delay3()) ,
new gate_instance(),
new Repetition(new Sequence(new Terminal(COMMA),
new gate_instance())) ,
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public gate_instantiation create() {
        return new gate_instantiation();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


