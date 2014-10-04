
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class select extends Acceptor implements NonTerminal, ITokenCodes {

    public select() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Optional(new Sequence(new Repetition(new Sequence(new Terminal(DOT),
new member_identifier(),
new bit_select())) ,
new Terminal(DOT),
new member_identifier())) ,
new bit_select(),
new Optional(new Sequence(new Terminal(LBRACK),
new part_select_range(),
new Terminal(RBRACK))) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public select create() {
        return new select();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


