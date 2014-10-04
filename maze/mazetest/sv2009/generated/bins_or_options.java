
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class bins_or_options extends Acceptor implements NonTerminal, ITokenCodes {

    public bins_or_options() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new coverage_option(),
new Sequence(new bins_keyword(),
new bin_identifier(),
new Terminal(EQ),
new Terminal(DEFAULT_K),
new Terminal(SEQUENCE_K),
new Optional(new Sequence(new Terminal(IFF_K),
new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN))) ),
new Sequence(new Optional(new Terminal(WILDCARD_K)) ,
new bins_keyword(),
new bin_identifier(),
new Optional(new Sequence(new Terminal(LBRACK),
new Optional(new expression()) ,
new Terminal(RBRACK))) ,
new Terminal(EQ),
new Terminal(LCURLY),
new open_range_list(),
new Terminal(RCURLY),
new Optional(new Sequence(new Terminal(IFF_K),
new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN))) ),
new Sequence(new Optional(new Terminal(WILDCARD_K)) ,
new bins_keyword(),
new bin_identifier(),
new Optional(new Sequence(new Terminal(RBRACK),
new Terminal(RBRACK))) ,
new Terminal(EQ),
new trans_list(),
new Optional(new Sequence(new Terminal(IFF_K),
new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN))) ),
new Sequence(new bins_keyword(),
new bin_identifier(),
new Optional(new Sequence(new Terminal(LBRACK),
new Optional(new expression()) ,
new Terminal(RBRACK))) ,
new Terminal(EQ),
new Terminal(DEFAULT_K),
new Optional(new Sequence(new Terminal(IFF_K),
new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN))) )) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public bins_or_options create() {
        return new bins_or_options();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


