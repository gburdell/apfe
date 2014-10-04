
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class implicit_class_handle extends Acceptor implements NonTerminal, ITokenCodes {

    public implicit_class_handle() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Terminal(THIS_K),
new Terminal(SUPER_K),
new Sequence(new Terminal(THIS_K),
new Terminal(DOT),
new Terminal(SUPER_K))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public implicit_class_handle create() {
        return new implicit_class_handle();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


