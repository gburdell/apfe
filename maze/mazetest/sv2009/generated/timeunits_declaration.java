
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class timeunits_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public timeunits_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(TIMEUNIT_K),
new Terminal(TIME_LITERAL),
new Terminal(SEMI),
new Terminal(TIMEPRECISION_K),
new Terminal(TIME_LITERAL),
new Terminal(SEMI)),
new Sequence(new Terminal(TIMEUNIT_K),
new Terminal(TIME_LITERAL),
new Optional(new Sequence(new Terminal(DIV),
new Terminal(TIME_LITERAL))) ,
new Terminal(SEMI)),
new Sequence(new Terminal(TIMEPRECISION_K),
new Terminal(TIME_LITERAL),
new Terminal(SEMI),
new Terminal(TIMEUNIT_K),
new Terminal(TIME_LITERAL),
new Terminal(SEMI)),
new Sequence(new Terminal(TIMEPRECISION_K),
new Terminal(TIME_LITERAL),
new Terminal(SEMI))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public timeunits_declaration create() {
        return new timeunits_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


