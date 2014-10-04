
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class config_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public config_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(CONFIG_K),
new config_identifier(),
new Terminal(SEMI),
new Repetition(new Sequence(new local_parameter_declaration(),
new Terminal(SEMI))) ,
new design_statement(),
new Repetition(new config_rule_statement()) ,
new Terminal(ENDCONFIG_K),
new Optional(new Sequence(new Terminal(COLON),
new config_identifier())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public config_declaration create() {
        return new config_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


