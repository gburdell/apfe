
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class randomize_call extends Acceptor implements NonTerminal, ITokenCodes {

    public randomize_call() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(RANDOMIZE_K),
new Repetition(new attribute_instance()) ,
new Optional(new Sequence(new Terminal(LPAREN),
new Optional(new Alternates(new variable_identifier_list(),
new Terminal(NULL_K))) ,
new Terminal(RPAREN))) ,
new Optional(new Sequence(new Terminal(WITH_K),
new Optional(new Sequence(new Terminal(LPAREN),
new Optional(new identifier_list()) ,
new Terminal(RPAREN))) ,
new constraint_block())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public randomize_call create() {
        return new randomize_call();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


