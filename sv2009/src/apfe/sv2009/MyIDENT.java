package apfe.sv2009;

import apfe.runtime.*;

import apfe.runtime.CharBuffer.Marker;
import apfe.sv2009.generated.IdentCont;
import apfe.sv2009.generated.IdentStart;
import java.util.HashMap;
import java.util.Map;

public class MyIDENT extends Acceptor {

    public MyIDENT() {
    }

    @Override
    protected boolean accepti() {
        (new MySpacing()).acceptTrue();
        CharBuffer.Marker mark = getCurrentMark();
        Acceptor matcher = new Sequence(new IdentStart(), new Repetition(new IdentCont(), Repetition.ERepeat.eZeroOrMore));
        Acceptor accepted = match(matcher);
        boolean match = (null != accepted);
        if (match) {
            String id = State.getTheOne().getBuf().substring(mark);
            match = !stKwrd.containsKey(id);
        }
        return match;
    }

    @Override
    public MyIDENT create() {
        return new MyIDENT();
    }

    //Begin memoize
    @Override
    protected void memoize(Marker mark, Marker endMark) {
        stMemo.add(mark, this, endMark);
    }

    @Override
    protected Memoize.Data hasMemoized(Marker mark) {
        return stMemo.memoized(mark);
    }
    /**
     * Memoize for all instances of MyIDENT.
     */
    private static final Memoize stMemo = new Memoize();
	//End memoize

    private final static Map<String, Boolean> stKwrd = new HashMap<>();

    // Use:
    // egrep '^[^ ]+_K[ ]+<-' sv2009.peg | awk 'BEGIN{printf"static {\n"}{printf"stKwrd.put(%s,true);\n",$4}END{printf"}\n"}'
    // to initialize
    static {
        stKwrd.put("accept_on", true);
        stKwrd.put("alias", true);
        stKwrd.put("always_comb", true);
        stKwrd.put("always_ff", true);
        stKwrd.put("always", true);
        stKwrd.put("always_latch", true);
        stKwrd.put("and", true);
        stKwrd.put("assert", true);
        stKwrd.put("assign", true);
        stKwrd.put("assume", true);
        stKwrd.put("automatic", true);
        stKwrd.put("before", true);
        stKwrd.put("begin", true);
        stKwrd.put("bind", true);
        stKwrd.put("binsof", true);
        stKwrd.put("bins", true);
        stKwrd.put("bit", true);
        stKwrd.put("break", true);
        stKwrd.put("bufif0", true);
        stKwrd.put("bufif1", true);
        stKwrd.put("buf", true);
        stKwrd.put("byte", true);
        stKwrd.put("casex", true);
        stKwrd.put("casez", true);
        stKwrd.put("case", true);
        stKwrd.put("cell", true);
        stKwrd.put("chandle", true);
        stKwrd.put("checker", true);
        stKwrd.put("class", true);
        stKwrd.put("clocking", true);
        stKwrd.put("cmos", true);
        stKwrd.put("config", true);
        stKwrd.put("constraint", true);
        stKwrd.put("const", true);
        stKwrd.put("context", true);
        stKwrd.put("continue", true);
        stKwrd.put("covergroup", true);
        stKwrd.put("coverpoint", true);
        stKwrd.put("cover", true);
        stKwrd.put("cross", true);
        stKwrd.put("deassign", true);
        stKwrd.put("default", true);
        stKwrd.put("defparam", true);
        stKwrd.put("design", true);
        stKwrd.put("disable", true);
        stKwrd.put("dist", true);
        stKwrd.put("do", true);
        stKwrd.put("edge", true);
        stKwrd.put("else", true);
        stKwrd.put("endcase", true);
        stKwrd.put("endchecker", true);
        stKwrd.put("endclass", true);
        stKwrd.put("endclocking", true);
        stKwrd.put("endconfig", true);
        stKwrd.put("endfunction", true);
        stKwrd.put("endgenerate", true);
        stKwrd.put("endgroup", true);
        stKwrd.put("endinterface", true);
        stKwrd.put("endmodule", true);
        stKwrd.put("endpackage", true);
        stKwrd.put("endprimitive", true);
        stKwrd.put("endprogram", true);
        stKwrd.put("endproperty", true);
        stKwrd.put("endsequence", true);
        stKwrd.put("endspecify", true);
        stKwrd.put("endtable", true);
        stKwrd.put("endtask", true);
        stKwrd.put("end", true);
        stKwrd.put("enum", true);
        stKwrd.put("eventually", true);
        stKwrd.put("event", true);
        stKwrd.put("expect", true);
        stKwrd.put("export", true);
        stKwrd.put("extends", true);
        stKwrd.put("extern", true);
        stKwrd.put("final", true);
        stKwrd.put("first_match", true);
        stKwrd.put("force", true);
        stKwrd.put("foreach", true);
        stKwrd.put("forever", true);
        stKwrd.put("forkjoin", true);
        stKwrd.put("fork", true);
        stKwrd.put("for", true);
        stKwrd.put("function", true);
        stKwrd.put("generate", true);
        stKwrd.put("genvar", true);
        stKwrd.put("global", true);
        stKwrd.put("highz0", true);
        stKwrd.put("highz1", true);
        stKwrd.put("iff", true);
        stKwrd.put("ifnone", true);
        stKwrd.put("if", true);
        stKwrd.put("ignore_bins", true);
        stKwrd.put("illegal_bins", true);
        stKwrd.put("implies", true);
        stKwrd.put("import", true);
        stKwrd.put("incdir", true);
        stKwrd.put("include", true);
        stKwrd.put("initial", true);
        stKwrd.put("inout", true);
        stKwrd.put("input", true);
        stKwrd.put("inside", true);
        stKwrd.put("instance", true);
        stKwrd.put("integer", true);
        stKwrd.put("interface", true);
        stKwrd.put("intersect", true);
        stKwrd.put("int", true);
        stKwrd.put("join_any", true);
        stKwrd.put("join", true);
        stKwrd.put("join_none", true);
        stKwrd.put("large", true);
        stKwrd.put("let", true);
        stKwrd.put("liblist", true);
        stKwrd.put("library", true);
        stKwrd.put("localparam", true);
        stKwrd.put("local", true);
        stKwrd.put("logic", true);
        stKwrd.put("longint", true);
        stKwrd.put("macromodule", true);
        stKwrd.put("matches", true);
        stKwrd.put("medium", true);
        stKwrd.put("modport", true);
        stKwrd.put("module", true);
        stKwrd.put("nand", true);
        stKwrd.put("negedge", true);
        stKwrd.put("new", true);
        stKwrd.put("nexttime", true);
        stKwrd.put("nmos", true);
        stKwrd.put("nor", true);
        stKwrd.put("noshowcancelled", true);
        stKwrd.put("notif0", true);
        stKwrd.put("notif1", true);
        stKwrd.put("not", true);
        stKwrd.put("null", true);
        stKwrd.put("or", true);
        stKwrd.put("output", true);
        stKwrd.put("package", true);
        stKwrd.put("packed", true);
        stKwrd.put("parameter", true);
        stKwrd.put("pmos", true);
        stKwrd.put("posedge", true);
        stKwrd.put("primitive", true);
        stKwrd.put("priority", true);
        stKwrd.put("program", true);
        stKwrd.put("property", true);
        stKwrd.put("protected", true);
        stKwrd.put("pull0", true);
        stKwrd.put("pull1", true);
        stKwrd.put("pulldown", true);
        stKwrd.put("pullup", true);
        stKwrd.put("pulsestyle_ondetect", true);
        stKwrd.put("pulsestyle_onevent", true);
        stKwrd.put("pure", true);
        stKwrd.put("randcase", true);
        stKwrd.put("randc", true);
        stKwrd.put("randsequence", true);
        stKwrd.put("rand", true);
        stKwrd.put("randomize", true);
        stKwrd.put("rcmos", true);
        stKwrd.put("realtime", true);
        stKwrd.put("real", true);
        stKwrd.put("ref", true);
        stKwrd.put("reg", true);
        stKwrd.put("reject_on", true);
        stKwrd.put("release", true);
        stKwrd.put("repeat", true);
        stKwrd.put("restrict", true);
        stKwrd.put("return", true);
        stKwrd.put("rnmos", true);
        stKwrd.put("rpmos", true);
        stKwrd.put("rtranif0", true);
        stKwrd.put("rtranif1", true);
        stKwrd.put("rtran", true);
        stKwrd.put("scalared", true);
        stKwrd.put("sequence", true);
        stKwrd.put("shortint", true);
        stKwrd.put("shortreal", true);
        stKwrd.put("showcancelled", true);
        stKwrd.put("signed", true);
        stKwrd.put("small", true);
        stKwrd.put("solve", true);
        stKwrd.put("specify", true);
        stKwrd.put("specparam", true);
        stKwrd.put("static", true);
        stKwrd.put("string", true);
        stKwrd.put("strong0", true);
        stKwrd.put("strong1", true);
        stKwrd.put("strong", true);
        stKwrd.put("struct", true);
        stKwrd.put("super", true);
        stKwrd.put("supply0", true);
        stKwrd.put("supply1", true);
        stKwrd.put("sync_accept_on", true);
        stKwrd.put("sync_reject_on", true);
        stKwrd.put("s_always", true);
        stKwrd.put("s_eventually", true);
        stKwrd.put("s_nexttime", true);
        stKwrd.put("s_until", true);
        stKwrd.put("s_until_with", true);
        stKwrd.put("table", true);
        stKwrd.put("tagged", true);
        stKwrd.put("task", true);
        stKwrd.put("this", true);
        stKwrd.put("throughout", true);
        stKwrd.put("timeprecision", true);
        stKwrd.put("timeunit", true);
        stKwrd.put("time", true);
        stKwrd.put("tranif0", true);
        stKwrd.put("tranif1", true);
        stKwrd.put("tran", true);
        stKwrd.put("tri0", true);
        stKwrd.put("tri1", true);
        stKwrd.put("triand", true);
        stKwrd.put("trior", true);
        stKwrd.put("trireg", true);
        stKwrd.put("tri", true);
        stKwrd.put("typedef", true);
        stKwrd.put("type", true);
        stKwrd.put("union", true);
        stKwrd.put("unique0", true);
        stKwrd.put("unique", true);
        stKwrd.put("unsigned", true);
        stKwrd.put("until", true);
        stKwrd.put("until_with", true);
        stKwrd.put("untyped", true);
        stKwrd.put("use", true);
        stKwrd.put("uwire", true);
        stKwrd.put("var", true);
        stKwrd.put("vectored", true);
        stKwrd.put("virtual", true);
        stKwrd.put("void", true);
        stKwrd.put("wait", true);
        stKwrd.put("wait_order", true);
        stKwrd.put("wand", true);
        stKwrd.put("weak0", true);
        stKwrd.put("weak1", true);
        stKwrd.put("weak", true);
        stKwrd.put("while", true);
        stKwrd.put("wildcard", true);
        stKwrd.put("wire", true);
        stKwrd.put("within", true);
        stKwrd.put("with", true);
        stKwrd.put("wor", true);
        stKwrd.put("xnor", true);
        stKwrd.put("xor", true);
        stKwrd.put("1step", true);
        stKwrd.put("PATHPULSE$", true);
        stKwrd.put("$setup", true);
        stKwrd.put("$removal", true);
        stKwrd.put("$period", true);
        stKwrd.put("$recrem", true);
        stKwrd.put("$setuphold", true);
        stKwrd.put("$recovery", true);
        stKwrd.put("$hold", true);
        stKwrd.put("$width", true);
        stKwrd.put("$skew", true);
        stKwrd.put("$fullskew", true);
        stKwrd.put("$timeskew", true);
        stKwrd.put("$nochange", true);
        stKwrd.put("$fatal", true);
        stKwrd.put("$error", true);
        stKwrd.put("$warning", true);
        stKwrd.put("$info", true);
        stKwrd.put("$unit", true);
        stKwrd.put("$root", true);
    }

}
