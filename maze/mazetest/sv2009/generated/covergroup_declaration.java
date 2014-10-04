
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class covergroup_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public covergroup_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(COVERGROUP_K),
new covergroup_identifier(),
new Optional(new Sequence(new Terminal(LPAREN),
new Optional(new tf_port_list()) ,
new Terminal(RPAREN))) ,
new Optional(new coverage_event()) ,
new Terminal(SEMI),
new Repetition(new coverage_spec_or_option()) ,
new Terminal(ENDGROUP_K),
new Optional(new Sequence(new Terminal(COLON),
new covergroup_identifier())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public covergroup_declaration create() {
        return new covergroup_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


