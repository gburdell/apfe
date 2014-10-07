
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class let_list_of_arguments extends Acceptor implements NonTerminal, ITokenCodes {

    public let_list_of_arguments() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(DOT),
new identifier(),
new Terminal(LPAREN),
new Optional(new let_actual_arg()) ,
new Terminal(RPAREN),
new Repetition(new Sequence(new Terminal(COMMA),
new Terminal(DOT),
new identifier(),
new Terminal(LPAREN),
new Optional(new let_actual_arg()) ,
new Terminal(RPAREN))) ),
new Sequence(new Optional(new let_actual_arg()) ,
new Repetition(new Sequence(new Terminal(COMMA),
new Optional(new let_actual_arg()) )) ,
new Repetition(new Sequence(new Terminal(COMMA),
new Terminal(DOT),
new identifier(),
new Terminal(LPAREN),
new Optional(new let_actual_arg()) ,
new Terminal(RPAREN))) )) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public let_list_of_arguments create() {
        return new let_list_of_arguments();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}

