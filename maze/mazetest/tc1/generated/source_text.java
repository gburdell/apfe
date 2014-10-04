
package apfe.maze.tc1.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.tc1.*;



public  class source_text extends Acceptor implements NonTerminal, ITokenCodes {

    public source_text() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(MODULE_K),
new module_identifier(),
new Optional(new list_of_ports()) ,
new Terminal(SEMI),
new bodys(),
new Terminal(ENDMODULE_K),
new EOF()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public source_text create() {
        return new source_text();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


