
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class dpi_import_export extends Acceptor implements NonTerminal, ITokenCodes {

    public dpi_import_export() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(IMPORT_K),
new dpi_spec_string(),
new Optional(new dpi_function_import_property()) ,
new Optional(new Sequence(new c_identifier(),
new Terminal(EQ))) ,
new dpi_function_proto(),
new Terminal(SEMI)),
new Sequence(new Terminal(IMPORT_K),
new dpi_spec_string(),
new Optional(new dpi_task_import_property()) ,
new Optional(new Sequence(new c_identifier(),
new Terminal(EQ))) ,
new dpi_task_proto(),
new Terminal(SEMI)),
new Sequence(new Terminal(EXPORT_K),
new dpi_spec_string(),
new Optional(new Sequence(new c_identifier(),
new Terminal(EQ))) ,
new Terminal(FUNCTION_K),
new function_identifier(),
new Terminal(SEMI)),
new Sequence(new Terminal(EXPORT_K),
new dpi_spec_string(),
new Optional(new Sequence(new c_identifier(),
new Terminal(EQ))) ,
new Terminal(TASK_K),
new task_identifier(),
new Terminal(SEMI))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public dpi_import_export create() {
        return new dpi_import_export();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


