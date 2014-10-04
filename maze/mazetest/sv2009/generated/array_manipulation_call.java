
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class array_manipulation_call extends Acceptor implements NonTerminal, ITokenCodes {

    public array_manipulation_call() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new array_method_name(),
new Repetition(new attribute_instance()) ,
new Optional(new Sequence(new Terminal(LPAREN),
new list_of_arguments(),
new Terminal(RPAREN))) ,
new Optional(new Sequence(new Terminal(WITH_K),
new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN))) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public array_manipulation_call create() {
        return new array_manipulation_call();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


