
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class ps_parameter_identifier extends Acceptor implements NonTerminal, ITokenCodes {

    public ps_parameter_identifier() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Repetition(new Sequence(new generate_block_identifier(),
new Optional(new Sequence(new Terminal(LBRACK),
new constant_expression(),
new Terminal(RBRACK))) ,
new Terminal(DOT))) ,
new parameter_identifier()),
new Sequence(new Optional(new Alternates(new package_scope(),
new class_scope())) ,
new parameter_identifier())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public ps_parameter_identifier create() {
        return new ps_parameter_identifier();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


