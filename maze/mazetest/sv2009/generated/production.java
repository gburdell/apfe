
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class production extends Acceptor implements NonTerminal, ITokenCodes {

    public production() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Optional(new data_type_or_void()) ,
new production_identifier(),
new Optional(new Sequence(new Terminal(LPAREN),
new tf_port_list(),
new Terminal(RPAREN))) ,
new Terminal(COLON),
new rs_rule(),
new Repetition(new Sequence(new Terminal(OR),
new rs_rule())) ,
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public production create() {
        return new production();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


