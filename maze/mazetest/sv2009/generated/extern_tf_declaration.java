
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class extern_tf_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public extern_tf_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(EXTERN_K),
new method_prototype(),
new Terminal(SEMI)),
new Sequence(new Terminal(EXTERN_K),
new Terminal(FORKJOIN_K),
new task_prototype(),
new Terminal(SEMI))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public extern_tf_declaration create() {
        return new extern_tf_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


