
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class drive_strength extends Acceptor implements NonTerminal, ITokenCodes {

    public drive_strength() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(LPAREN),
new strength0(),
new Terminal(COMMA),
new strength1(),
new Terminal(RPAREN)),
new Sequence(new Terminal(LPAREN),
new strength1(),
new Terminal(COMMA),
new strength0(),
new Terminal(RPAREN)),
new Sequence(new Terminal(LPAREN),
new strength0(),
new Terminal(COMMA),
new Terminal(HIGHZ1_K),
new Terminal(RPAREN)),
new Sequence(new Terminal(LPAREN),
new strength1(),
new Terminal(COMMA),
new Terminal(HIGHZ0_K),
new Terminal(RPAREN)),
new Sequence(new Terminal(LPAREN),
new Terminal(HIGHZ0_K),
new Terminal(COMMA),
new strength1(),
new Terminal(RPAREN)),
new Sequence(new Terminal(LPAREN),
new Terminal(HIGHZ1_K),
new Terminal(COMMA),
new strength0(),
new Terminal(RPAREN))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public drive_strength create() {
        return new drive_strength();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


