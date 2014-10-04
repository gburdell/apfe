
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class pattern extends Acceptor implements NonTerminal, ITokenCodes {

    public pattern() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Terminal(DOT_STAR),
new Sequence(new Terminal(TAGGED_K),
new member_identifier(),
new Optional(new pattern()) ),
new Sequence(new Terminal(SQUOTE),
new Terminal(LCURLY),
new member_identifier(),
new Terminal(COLON),
new pattern(),
new Repetition(new Sequence(new Terminal(COMMA),
new member_identifier(),
new Terminal(COLON),
new pattern())) ,
new Terminal(RCURLY)),
new Sequence(new Terminal(SQUOTE),
new Terminal(LCURLY),
new pattern(),
new Repetition(new Sequence(new Terminal(COMMA),
new pattern())) ,
new Terminal(RCURLY)),
new Sequence(new Terminal(DOT),
new variable_identifier()),
new constant_expression()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public pattern create() {
        return new pattern();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


