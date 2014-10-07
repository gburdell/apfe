
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class pulsestyle_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public pulsestyle_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(PULSESTYLE_ONEVENT_K),
new list_of_path_outputs(),
new Terminal(SEMI)),
new Sequence(new Terminal(PULSESTYLE_ONDETECT_K),
new list_of_path_outputs(),
new Terminal(SEMI))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public pulsestyle_declaration create() {
        return new pulsestyle_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}

