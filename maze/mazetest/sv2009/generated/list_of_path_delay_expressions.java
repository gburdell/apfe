
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class list_of_path_delay_expressions extends Acceptor implements NonTerminal, ITokenCodes {

    public list_of_path_delay_expressions() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new t01_path_delay_expression(),
new Terminal(COMMA),
new t10_path_delay_expression(),
new Terminal(COMMA),
new t0z_path_delay_expression(),
new Terminal(COMMA),
new tz1_path_delay_expression(),
new Terminal(COMMA),
new t1z_path_delay_expression(),
new Terminal(COMMA),
new tz0_path_delay_expression(),
new Terminal(COMMA),
new t0x_path_delay_expression(),
new Terminal(COMMA),
new tx1_path_delay_expression(),
new Terminal(COMMA),
new t1x_path_delay_expression(),
new Terminal(COMMA),
new tx0_path_delay_expression(),
new Terminal(COMMA),
new txz_path_delay_expression(),
new Terminal(COMMA),
new tzx_path_delay_expression()),
new Sequence(new t01_path_delay_expression(),
new Terminal(COMMA),
new t10_path_delay_expression(),
new Terminal(COMMA),
new t0z_path_delay_expression(),
new Terminal(COMMA),
new tz1_path_delay_expression(),
new Terminal(COMMA),
new t1z_path_delay_expression(),
new Terminal(COMMA),
new tz0_path_delay_expression()),
new Sequence(new trise_path_delay_expression(),
new Terminal(COMMA),
new tfall_path_delay_expression(),
new Terminal(COMMA),
new tz_path_delay_expression()),
new Sequence(new trise_path_delay_expression(),
new Terminal(COMMA),
new tfall_path_delay_expression()),
new t_path_delay_expression()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public list_of_path_delay_expressions create() {
        return new list_of_path_delay_expressions();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


