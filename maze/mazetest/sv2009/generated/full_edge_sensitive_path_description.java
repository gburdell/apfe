
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class full_edge_sensitive_path_description extends Acceptor implements NonTerminal, ITokenCodes {

    public full_edge_sensitive_path_description() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(LPAREN),
new Optional(new edge_identifier()) ,
new list_of_path_inputs(),
new Optional(new polarity_operator()) ,
new Terminal(STAR_GT),
new Terminal(LPAREN),
new list_of_path_outputs(),
new Optional(new polarity_operator()) ,
new Terminal(COLON),
new data_source_expression(),
new Terminal(RPAREN),
new Terminal(RPAREN)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public full_edge_sensitive_path_description create() {
        return new full_edge_sensitive_path_description();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


