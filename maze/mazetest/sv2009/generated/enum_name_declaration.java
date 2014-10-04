
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class enum_name_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public enum_name_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new enum_identifier(),
new Optional(new Sequence(new Terminal(LBRACK),
new Terminal(NUMBER),
new Optional(new Sequence(new Terminal(COLON),
new Terminal(NUMBER))) ,
new Terminal(RBRACK))) ,
new Optional(new Sequence(new Terminal(EQ),
new constant_expression())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public enum_name_declaration create() {
        return new enum_name_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


