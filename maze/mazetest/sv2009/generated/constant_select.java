
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class constant_select extends Acceptor implements NonTerminal, ITokenCodes {

    public constant_select() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Optional(new Sequence(new Repetition(new Sequence(new Terminal(DOT),
new member_identifier(),
new constant_bit_select())) ,
new Terminal(DOT),
new member_identifier())) ,
new constant_bit_select(),
new Optional(new Sequence(new Terminal(LBRACK),
new constant_part_select_range(),
new Terminal(RBRACK))) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public constant_select create() {
        return new constant_select();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}

