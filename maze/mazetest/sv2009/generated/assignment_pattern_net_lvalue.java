
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class assignment_pattern_net_lvalue extends Acceptor implements NonTerminal, ITokenCodes {

    public assignment_pattern_net_lvalue() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(SQUOTE),
new Terminal(LCURLY),
new net_lvalue(),
new Repetition(new Sequence(new Terminal(COMMA),
new net_lvalue())) ,
new Terminal(RCURLY)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public assignment_pattern_net_lvalue create() {
        return new assignment_pattern_net_lvalue();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


