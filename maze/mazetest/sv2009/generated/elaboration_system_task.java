
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class elaboration_system_task extends Acceptor implements NonTerminal, ITokenCodes {

    public elaboration_system_task() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(DS_FATAL_K),
new Optional(new Sequence(new Terminal(LPAREN),
new Terminal(UNSIGNED_NUMBER),
new Optional(new Sequence(new Terminal(COMMA),
new list_of_arguments())) ,
new Terminal(RPAREN))) ,
new Terminal(SEMI)),
new Sequence(new Terminal(DS_ERROR_K),
new Optional(new Sequence(new Terminal(LPAREN),
new list_of_arguments(),
new Terminal(RPAREN))) ,
new Terminal(SEMI)),
new Sequence(new Terminal(DS_WARNING_K),
new Optional(new Sequence(new Terminal(LPAREN),
new list_of_arguments(),
new Terminal(RPAREN))) ,
new Terminal(SEMI)),
new Sequence(new Terminal(DS_INFO_K),
new Optional(new Sequence(new Terminal(LPAREN),
new list_of_arguments(),
new Terminal(RPAREN))) ,
new Terminal(SEMI))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public elaboration_system_task create() {
        return new elaboration_system_task();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


