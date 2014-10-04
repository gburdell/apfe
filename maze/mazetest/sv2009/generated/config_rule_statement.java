
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class config_rule_statement extends Acceptor implements NonTerminal, ITokenCodes {

    public config_rule_statement() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new default_clause(),
new liblist_clause(),
new Terminal(SEMI)),
new Sequence(new inst_clause(),
new liblist_clause(),
new Terminal(SEMI)),
new Sequence(new inst_clause(),
new use_clause(),
new Terminal(SEMI)),
new Sequence(new cell_clause(),
new liblist_clause(),
new Terminal(SEMI)),
new Sequence(new cell_clause(),
new use_clause(),
new Terminal(SEMI))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public config_rule_statement create() {
        return new config_rule_statement();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


