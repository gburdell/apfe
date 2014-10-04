
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class seq_block extends Acceptor implements NonTerminal, ITokenCodes {

    public seq_block() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(BEGIN_K),
new Optional(new Sequence(new Terminal(COLON),
new block_identifier())) ,
new Repetition(new block_item_declaration()) ,
new Repetition(new statement_or_null()) ,
new Terminal(END_K),
new Optional(new Sequence(new Terminal(COLON),
new block_identifier())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public seq_block create() {
        return new seq_block();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


