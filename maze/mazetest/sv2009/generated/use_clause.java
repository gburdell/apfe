
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class use_clause extends Acceptor implements NonTerminal, ITokenCodes {

    public use_clause() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(USE_K),
new Optional(new Sequence(new library_identifier(),
new Terminal(DOT))) ,
new cell_identifier(),
new named_parameter_assignment(),
new Repetition(new Sequence(new Terminal(COMMA),
new named_parameter_assignment())) ,
new Optional(new Sequence(new Terminal(COLON),
new Terminal(CONFIG_K))) ),
new Sequence(new Terminal(USE_K),
new named_parameter_assignment(),
new Repetition(new Sequence(new Terminal(COMMA),
new named_parameter_assignment())) ,
new Optional(new Sequence(new Terminal(COLON),
new Terminal(CONFIG_K))) ),
new Sequence(new Terminal(USE_K),
new Optional(new Sequence(new library_identifier(),
new Terminal(DOT))) ,
new cell_identifier(),
new Optional(new Sequence(new Terminal(COLON),
new Terminal(CONFIG_K))) )) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public use_clause create() {
        return new use_clause();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


