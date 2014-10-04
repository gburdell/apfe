
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class checker_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public checker_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(CHECKER_K),
new checker_identifier(),
new Optional(new Sequence(new Terminal(LPAREN),
new Optional(new checker_port_list()) ,
new Terminal(RPAREN))) ,
new Terminal(SEMI),
new Repetition(new checker_or_generate_item()) ,
new Terminal(ENDCHECKER_K),
new Optional(new Sequence(new Terminal(COLON),
new checker_identifier())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public checker_declaration create() {
        return new checker_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


