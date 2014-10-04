
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class generate_block extends Acceptor implements NonTerminal, ITokenCodes {

    public generate_block() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Optional(new Sequence(new generate_block_identifier(),
new Terminal(COLON))) ,
new Terminal(BEGIN_K),
new Optional(new Sequence(new Terminal(COLON),
new generate_block_identifier())) ,
new Repetition(new generate_item()) ,
new Terminal(END_K),
new Optional(new Sequence(new Terminal(COLON),
new generate_block_identifier())) ),
new generate_item()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public generate_block create() {
        return new generate_block();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


