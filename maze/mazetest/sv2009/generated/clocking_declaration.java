
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class clocking_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public clocking_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(GLOBAL_K),
new Terminal(CLOCKING_K),
new Optional(new clocking_identifier()) ,
new clocking_event(),
new Terminal(SEMI),
new Terminal(ENDCLOCKING_K),
new Optional(new Sequence(new Terminal(COLON),
new clocking_identifier())) ),
new Sequence(new Optional(new Terminal(DEFAULT_K)) ,
new Terminal(CLOCKING_K),
new Optional(new clocking_identifier()) ,
new clocking_event(),
new Terminal(SEMI),
new Repetition(new clocking_item()) ,
new Terminal(ENDCLOCKING_K),
new Optional(new Sequence(new Terminal(COLON),
new clocking_identifier())) )) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public clocking_declaration create() {
        return new clocking_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


