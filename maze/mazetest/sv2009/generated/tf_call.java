
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class tf_call extends Acceptor implements NonTerminal, ITokenCodes {

    public tf_call() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new ps_or_hierarchical_tf_identifier(),
new Repetition(new attribute_instance()) ,
new Optional(new Sequence(new Terminal(LPAREN),
new list_of_arguments(),
new Terminal(RPAREN))) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public tf_call create() {
        return new tf_call();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


