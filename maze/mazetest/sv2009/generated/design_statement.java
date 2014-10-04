
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class design_statement extends Acceptor implements NonTerminal, ITokenCodes {

    public design_statement() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(DESIGN_K),
new Repetition(new Sequence(new Optional(new Sequence(new library_identifier(),
new Terminal(DOT))) ,
new cell_identifier())) ,
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public design_statement create() {
        return new design_statement();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


