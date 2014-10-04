
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class checker_instantiation extends Acceptor implements NonTerminal, ITokenCodes {

    public checker_instantiation() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new checker_identifier(),
new name_of_instance(),
new Terminal(LPAREN),
new Optional(new list_of_checker_port_connections()) ,
new Terminal(RPAREN),
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public checker_instantiation create() {
        return new checker_instantiation();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


