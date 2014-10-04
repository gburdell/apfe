
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class module_nonansi_header extends Acceptor implements NonTerminal, ITokenCodes {

    public module_nonansi_header() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Repetition(new attribute_instance()) ,
new module_keyword(),
new Optional(new lifetime()) ,
new module_identifier(),
new Repetition(new package_import_declaration()) ,
new Optional(new parameter_port_list()) ,
new list_of_ports(),
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public module_nonansi_header create() {
        return new module_nonansi_header();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


