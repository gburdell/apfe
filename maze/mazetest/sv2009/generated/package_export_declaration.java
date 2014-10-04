
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class package_export_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public package_export_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(EXPORT_K),
new Terminal(STAR_COLON2_STAR),
new Terminal(SEMI)),
new Sequence(new Terminal(EXPORT_K),
new package_import_item(),
new Repetition(new Sequence(new Terminal(COMMA),
new package_import_item())) ,
new Terminal(SEMI))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public package_export_declaration create() {
        return new package_export_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


