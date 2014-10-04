
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class method_call_body extends Acceptor implements NonTerminal, ITokenCodes {

    public method_call_body() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new method_identifier(),
new Repetition(new attribute_instance()) ,
new Optional(new Sequence(new Terminal(LPAREN),
new list_of_arguments(),
new Terminal(RPAREN))) ),
new built_in_method_call()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public method_call_body create() {
        return new method_call_body();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


