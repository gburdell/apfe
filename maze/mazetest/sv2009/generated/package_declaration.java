
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class package_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public package_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Repetition(new attribute_instance()) ,
new Terminal(PACKAGE_K),
new Optional(new lifetime()) ,
new package_identifier(),
new Terminal(SEMI),
new Optional(new timeunits_declaration()) ,
new Repetition(new Sequence(new Repetition(new attribute_instance()) ,
new package_item())) ,
new Terminal(ENDPACKAGE_K),
new Optional(new Sequence(new Terminal(COLON),
new package_identifier())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public package_declaration create() {
        return new package_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


