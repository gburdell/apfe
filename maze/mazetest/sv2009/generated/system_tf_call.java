
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class system_tf_call extends Acceptor implements NonTerminal, ITokenCodes {

    public system_tf_call() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new system_tf_identifier(),
new Terminal(LPAREN),
new data_type(),
new Optional(new Sequence(new Terminal(COMMA),
new expression())) ,
new Terminal(RPAREN)),
new Sequence(new system_tf_identifier(),
new Optional(new Sequence(new Terminal(LPAREN),
new list_of_arguments(),
new Terminal(RPAREN))) )) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public system_tf_call create() {
        return new system_tf_call();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


