
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class nonrange_select extends Acceptor implements NonTerminal, ITokenCodes {

    public nonrange_select() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Optional(new Sequence(new Repetition(new Sequence(new Terminal(DOT),
new member_identifier(),
new bit_select())) ,
new Terminal(DOT),
new member_identifier())) ,
new bit_select()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public nonrange_select create() {
        return new nonrange_select();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


