
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class disable_statement extends Acceptor implements NonTerminal, ITokenCodes {

    public disable_statement() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(DISABLE_K),
new Terminal(FORK_K),
new Terminal(SEMI)),
new Sequence(new Terminal(DISABLE_K),
new hierarchical_task_identifier(),
new Terminal(SEMI)),
new Sequence(new Terminal(DISABLE_K),
new hierarchical_block_identifier(),
new Terminal(SEMI))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public disable_statement create() {
        return new disable_statement();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


