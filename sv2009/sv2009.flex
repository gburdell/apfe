package apfe.sv2009;
import apfe.runtime.Scanner;
import apfe.runtime.Token;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

%%

%public
%class SvScanner
%extends    Scanner
%implements ITokenCodes
%unicode
%line
%column
%function xnextToken
%type Token

%{
  private final StringBuilder string = new StringBuilder();

  private static String stFileName = "?";

  public static void setFileName(String fn) {
  	stFileName = fn;
  }

  public static String getFileName() {
  	return stFileName;
  }

  private Token create(int id, String text) {
  	  	return new Token(getFileName(), yyline, yycolumn+1, text, id);
  }

  private Token create(int id) {
  	  	return create(id, yytext());
  }

  private void ticLine() {
  	StringTokenizer toks = new StringTokenizer(yytext());
	assert toks.hasMoreElements();
	toks.nextToken(); //skip `line
	assert toks.hasMoreElements();
	int lnum = Integer.parseInt(toks.nextToken());
	assert toks.hasMoreElements();
	String fname = toks.nextToken().replaceAll("\"","");//lose lead/trail "
	assert toks.hasMoreElements();
	yyline = lnum-1;
	stFileName = fname;
  }

  public SvScanner(String fn) throws FileNotFoundException, IOException {
	this(new FileReader(fn));
    setFileName(fn);
	slurp();
	yyclose();
  }

  private void error(String msg) {
      StringBuilder sb = new StringBuilder();
      sb.append(getFileName()).append(':').append(yyline).append(':')
              .append(yycolumn+1).append(": ").append(msg)
              .append(": ").append(yytext());
      throw new RuntimeException(sb.toString());
  }

    @Override
    public Token nextToken() {
        try {
            return xnextToken();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
  
  @Override
  public boolean isEOF() {
  	return zzAtEOF;
	}

	@Override
	public String getAsString(int tokCode) {
		return stMap.get(tokCode);
	}

	private static final HashMap<Integer,String> stMap = new HashMap<>();
	static {
		stMap.put(ACCEPT_ON_K,"accept_on");
		stMap.put(ALIAS_K,"alias");
		stMap.put(ALWAYS_COMB_K,"always_comb");
		stMap.put(ALWAYS_FF_K,"always_ff");
		stMap.put(ALWAYS_K,"always");
		stMap.put(ALWAYS_LATCH_K,"always_latch");
		stMap.put(AND_K,"and");
		stMap.put(ASSERT_K,"assert");
		stMap.put(ASSIGN_K,"assign");
		stMap.put(ASSUME_K,"assume");
		stMap.put(AUTOMATIC_K,"automatic");
		stMap.put(BEFORE_K,"before");
		stMap.put(BEGIN_K,"begin");
		stMap.put(BIND_K,"bind");
		stMap.put(BINSOF_K,"binsof");
		stMap.put(BINS_K,"bins");
		stMap.put(BIT_K,"bit");
		stMap.put(BREAK_K,"break");
		stMap.put(BUFIF0_K,"bufif0");
		stMap.put(BUFIF1_K,"bufif1");
		stMap.put(BUF_K,"buf");
		stMap.put(BYTE_K,"byte");
		stMap.put(CASEX_K,"casex");
		stMap.put(CASEZ_K,"casez");
		stMap.put(CASE_K,"case");
		stMap.put(CELL_K,"cell");
		stMap.put(CHANDLE_K,"chandle");
		stMap.put(CHECKER_K,"checker");
		stMap.put(CLASS_K,"class");
		stMap.put(CLOCKING_K,"clocking");
		stMap.put(CMOS_K,"cmos");
		stMap.put(CONFIG_K,"config");
		stMap.put(CONSTRAINT_K,"constraint");
		stMap.put(CONST_K,"const");
		stMap.put(CONTEXT_K,"context");
		stMap.put(CONTINUE_K,"continue");
		stMap.put(COVERGROUP_K,"covergroup");
		stMap.put(COVERPOINT_K,"coverpoint");
		stMap.put(COVER_K,"cover");
		stMap.put(CROSS_K,"cross");
		stMap.put(DEASSIGN_K,"deassign");
		stMap.put(DEFAULT_K,"default");
		stMap.put(DEFPARAM_K,"defparam");
		stMap.put(DESIGN_K,"design");
		stMap.put(DISABLE_K,"disable");
		stMap.put(DIST_K,"dist");
		stMap.put(DO_K,"do");
		stMap.put(EDGE_K,"edge");
		stMap.put(ELSE_K,"else");
		stMap.put(ENDCASE_K,"endcase");
		stMap.put(ENDCHECKER_K,"endchecker");
		stMap.put(ENDCLASS_K,"endclass");
		stMap.put(ENDCLOCKING_K,"endclocking");
		stMap.put(ENDCONFIG_K,"endconfig");
		stMap.put(ENDFUNCTION_K,"endfunction");
		stMap.put(ENDGENERATE_K,"endgenerate");
		stMap.put(ENDGROUP_K,"endgroup");
		stMap.put(ENDINTERFACE_K,"endinterface");
		stMap.put(ENDMODULE_K,"endmodule");
		stMap.put(ENDPACKAGE_K,"endpackage");
		stMap.put(ENDPRIMITIVE_K,"endprimitive");
		stMap.put(ENDPROGRAM_K,"endprogram");
		stMap.put(ENDPROPERTY_K,"endproperty");
		stMap.put(ENDSEQUENCE_K,"endsequence");
		stMap.put(ENDSPECIFY_K,"endspecify");
		stMap.put(ENDTABLE_K,"endtable");
		stMap.put(ENDTASK_K,"endtask");
		stMap.put(END_K,"end");
		stMap.put(ENUM_K,"enum");
		stMap.put(EVENTUALLY_K,"eventually");
		stMap.put(EVENT_K,"event");
		stMap.put(EXPECT_K,"expect");
		stMap.put(EXPORT_K,"export");
		stMap.put(EXTENDS_K,"extends");
		stMap.put(EXTERN_K,"extern");
		stMap.put(FINAL_K,"final");
		stMap.put(FIRST_MATCH_K,"first_match");
		stMap.put(FORCE_K,"force");
		stMap.put(FOREACH_K,"foreach");
		stMap.put(FOREVER_K,"forever");
		stMap.put(FORKJOIN_K,"forkjoin");
		stMap.put(FORK_K,"fork");
		stMap.put(FOR_K,"for");
		stMap.put(FUNCTION_K,"function");
		stMap.put(GENERATE_K,"generate");
		stMap.put(GENVAR_K,"genvar");
		stMap.put(GLOBAL_K,"global");
		stMap.put(HIGHZ0_K,"highz0");
		stMap.put(HIGHZ1_K,"highz1");
		stMap.put(IFF_K,"iff");
		stMap.put(IFNONE_K,"ifnone");
		stMap.put(IF_K,"if");
		stMap.put(IGNORE_BINS_K,"ignore_bins");
		stMap.put(ILLEGAL_BINS_K,"illegal_bins");
		stMap.put(IMPLIES_K,"implies");
		stMap.put(IMPORT_K,"import");
		stMap.put(INCDIR_K,"incdir");
		stMap.put(INCLUDE_K,"include");
		stMap.put(INITIAL_K,"initial");
		stMap.put(INOUT_K,"inout");
		stMap.put(INPUT_K,"input");
		stMap.put(INSIDE_K,"inside");
		stMap.put(INSTANCE_K,"instance");
		stMap.put(INTEGER_K,"integer");
		stMap.put(INTERFACE_K,"interface");
		stMap.put(INTERSECT_K,"intersect");
		stMap.put(INT_K,"int");
		stMap.put(JOIN_ANY_K,"join_any");
		stMap.put(JOIN_K,"join");
		stMap.put(JOIN_NONE_K,"join_none");
		stMap.put(LARGE_K,"large");
		stMap.put(LET_K,"let");
		stMap.put(LIBLIST_K,"liblist");
		stMap.put(LIBRARY_K,"library");
		stMap.put(LOCALPARAM_K,"localparam");
		stMap.put(LOCAL_K,"local");
		stMap.put(LOGIC_K,"logic");
		stMap.put(LONGINT_K,"longint");
		stMap.put(MACROMODULE_K,"macromodule");
		stMap.put(MATCHES_K,"matches");
		stMap.put(MEDIUM_K,"medium");
		stMap.put(MODPORT_K,"modport");
		stMap.put(MODULE_K,"module");
		stMap.put(NAND_K,"nand");
		stMap.put(NEGEDGE_K,"negedge");
		stMap.put(NEW_K,"new");
		stMap.put(NEXTTIME_K,"nexttime");
		stMap.put(NMOS_K,"nmos");
		stMap.put(NOR_K,"nor");
		stMap.put(NOSHOWCANCELLED_K,"noshowcancelled");
		stMap.put(NOTIF0_K,"notif0");
		stMap.put(NOTIF1_K,"notif1");
		stMap.put(NOT_K,"not");
		stMap.put(NULL_K,"null");
		stMap.put(OR_K,"or");
		stMap.put(OUTPUT_K,"output");
		stMap.put(PACKAGE_K,"package");
		stMap.put(PACKED_K,"packed");
		stMap.put(PARAMETER_K,"parameter");
		stMap.put(PMOS_K,"pmos");
		stMap.put(POSEDGE_K,"posedge");
		stMap.put(PRIMITIVE_K,"primitive");
		stMap.put(PRIORITY_K,"priority");
		stMap.put(PROGRAM_K,"program");
		stMap.put(PROPERTY_K,"property");
		stMap.put(PROTECTED_K,"protected");
		stMap.put(PULL0_K,"pull0");
		stMap.put(PULL1_K,"pull1");
		stMap.put(PULLDOWN_K,"pulldown");
		stMap.put(PULLUP_K,"pullup");
		stMap.put(PULSESTYLE_ONDETECT_K,"pulsestyle_ondetect");
		stMap.put(PULSESTYLE_ONEVENT_K,"pulsestyle_onevent");
		stMap.put(PURE_K,"pure");
		stMap.put(RANDCASE_K,"randcase");
		stMap.put(RANDC_K,"randc");
		stMap.put(RANDSEQUENCE_K,"randsequence");
		stMap.put(RAND_K,"rand");
		stMap.put(RANDOMIZE_K,"randomize");
		stMap.put(RCMOS_K,"rcmos");
		stMap.put(REALTIME_K,"realtime");
		stMap.put(REAL_K,"real");
		stMap.put(REF_K,"ref");
		stMap.put(REG_K,"reg");
		stMap.put(REJECT_ON_K,"reject_on");
		stMap.put(RELEASE_K,"release");
		stMap.put(REPEAT_K,"repeat");
		stMap.put(RESTRICT_K,"restrict");
		stMap.put(RETURN_K,"return");
		stMap.put(RNMOS_K,"rnmos");
		stMap.put(RPMOS_K,"rpmos");
		stMap.put(RTRANIF0_K,"rtranif0");
		stMap.put(RTRANIF1_K,"rtranif1");
		stMap.put(RTRAN_K,"rtran");
		stMap.put(SCALARED_K,"scalared");
		stMap.put(SEQUENCE_K,"sequence");
		stMap.put(SHORTINT_K,"shortint");
		stMap.put(SHORTREAL_K,"shortreal");
		stMap.put(SHOWCANCELLED_K,"showcancelled");
		stMap.put(SIGNED_K,"signed");
		stMap.put(SMALL_K,"small");
		stMap.put(SOLVE_K,"solve");
		stMap.put(SPECIFY_K,"specify");
		stMap.put(SPECPARAM_K,"specparam");
		stMap.put(STATIC_K,"static");
		stMap.put(STD_K,"std");
		stMap.put(STRING_K,"string");
		stMap.put(STRONG0_K,"strong0");
		stMap.put(STRONG1_K,"strong1");
		stMap.put(STRONG_K,"strong");
		stMap.put(STRUCT_K,"struct");
		stMap.put(SUPER_K,"super");
		stMap.put(SUPPLY0_K,"supply0");
		stMap.put(SUPPLY1_K,"supply1");
		stMap.put(SYNC_ACCEPT_ON_K,"sync_accept_on");
		stMap.put(SYNC_REJECT_ON_K,"sync_reject_on");
		stMap.put(S_ALWAYS_K,"s_always");
		stMap.put(S_EVENTUALLY_K,"s_eventually");
		stMap.put(S_NEXTTIME_K,"s_nexttime");
		stMap.put(S_UNTIL_K,"s_until");
		stMap.put(S_UNTIL_WITH_K,"s_until_with");
		stMap.put(TABLE_K,"table");
		stMap.put(TAGGED_K,"tagged");
		stMap.put(TASK_K,"task");
		stMap.put(THIS_K,"this");
		stMap.put(THROUGHOUT_K,"throughout");
		stMap.put(TIMEPRECISION_K,"timeprecision");
		stMap.put(TIMEUNIT_K,"timeunit");
		stMap.put(TIME_K,"time");
		stMap.put(TRANIF0_K,"tranif0");
		stMap.put(TRANIF1_K,"tranif1");
		stMap.put(TRAN_K,"tran");
		stMap.put(TRI0_K,"tri0");
		stMap.put(TRI1_K,"tri1");
		stMap.put(TRIAND_K,"triand");
		stMap.put(TRIOR_K,"trior");
		stMap.put(TRIREG_K,"trireg");
		stMap.put(TRI_K,"tri");
		stMap.put(TYPEDEF_K,"typedef");
		stMap.put(TYPE_K,"type");
		stMap.put(UNION_K,"union");
		stMap.put(UNIQUE0_K,"unique0");
		stMap.put(UNIQUE_K,"unique");
		stMap.put(UNSIGNED_K,"unsigned");
		stMap.put(UNTIL_K,"until");
		stMap.put(UNTIL_WITH_K,"until_with");
		stMap.put(UNTYPED_K,"untyped");
		stMap.put(USE_K,"use");
		stMap.put(UWIRE_K,"uwire");
		stMap.put(VAR_K,"var");
		stMap.put(VECTORED_K,"vectored");
		stMap.put(VIRTUAL_K,"virtual");
		stMap.put(VOID_K,"void");
		stMap.put(WAIT_K,"wait");
		stMap.put(WAIT_ORDER_K,"wait_order");
		stMap.put(WAND_K,"wand");
		stMap.put(WEAK0_K,"weak0");
		stMap.put(WEAK1_K,"weak1");
		stMap.put(WEAK_K,"weak");
		stMap.put(WHILE_K,"while");
		stMap.put(WILDCARD_K,"wildcard");
		stMap.put(WIRE_K,"wire");
		stMap.put(WITHIN_K,"within");
		stMap.put(WITH_K,"with");
		stMap.put(WOR_K,"wor");
		stMap.put(XNOR_K,"xnor");
		stMap.put(XOR_K,"xor");
		stMap.put(W1STEP_K,"1step");
		stMap.put(PATHPULSE_K,"PATHPULSE$");
		stMap.put(DS_SETUP_K,"$setup");
		stMap.put(DS_REMOVAL_K,"$removal");
		stMap.put(DS_PERIOD_K,"$period");
		stMap.put(DS_RECREM_K,"$recrem");
		stMap.put(DS_SETUPHOLD_K,"$setuphold");
		stMap.put(DS_RECOVERY_K,"$recovery");
		stMap.put(DS_HOLD_K,"$hold");
		stMap.put(DS_WIDTH_K,"$width");
		stMap.put(DS_SKEW_K,"$skew");
		stMap.put(DS_FULLSKEW_K,"$fullskew");
		stMap.put(DS_TIMESKEW_K,"$timeskew");
		stMap.put(DS_NOCHANGE_K,"$nochange");
		stMap.put(DS_FATAL_K,"$fatal");
		stMap.put(DS_ERROR_K,"$error");
		stMap.put(DS_WARNING_K,"$warning");
		stMap.put(DS_INFO_K,"$info");
		stMap.put(DS_UNIT_K,"$unit");
		stMap.put(DS_ROOT_K,"$root");
		stMap.put(AND,"&");
		stMap.put(AND2,"&&");
		stMap.put(AND3,"&&&");
		stMap.put(AND_EQ,"&=");
		stMap.put(AT,"@");
		stMap.put(AT2,"@@");
		stMap.put(AT_STAR,"@*");
		stMap.put(BAR_EQ_GT,"|=>");
		stMap.put(BAR_MINUS_GT,"|->");
		stMap.put(COLON2,"::");
		stMap.put(COLON,":");
		stMap.put(COLON_DIV,":/");
		stMap.put(COLON_EQ,":=");
		stMap.put(COMMA,",");
		stMap.put(DIV,"/");
		stMap.put(DIV_EQ,"/=");
		stMap.put(DOLLAR,"$");
		stMap.put(DOT,".");
		stMap.put(DOT_STAR,".*");
		stMap.put(EQ2,"==");
		stMap.put(EQ2_QMARK,"==?");
		stMap.put(EQ3,"===");
		stMap.put(EQ,"=");
		stMap.put(EQ_GT,"=>");
		stMap.put(GT2,">>");
		stMap.put(GT2_EQ,">>=");
		stMap.put(GT3_EQ,">>>=");
		stMap.put(GT,">");
		stMap.put(GT3,">>>");
		stMap.put(GT_EQ,">=");
		stMap.put(LBRACK,"[");
		stMap.put(LCURLY,"{");
		stMap.put(LPAREN,"(");
		stMap.put(LPAREN_STAR,"(*");
		stMap.put(LT2,"<<");
		stMap.put(LT2_EQ,"<<=");
		stMap.put(LT3_EQ,"<<<=");
		stMap.put(LT,"<");
		stMap.put(LT3,"<<<");
		stMap.put(LT_EQ,"<=");
		stMap.put(LT_MINUS_GT,"<->");
		stMap.put(MINUS2,"--");
		stMap.put(MINUS,"-");
		stMap.put(MINUS_COLON,"-:");
		stMap.put(MINUS_EQ,"-=");
		stMap.put(MINUS_GT2,"->>");
		stMap.put(MINUS_GT,"->");
		stMap.put(MOD,"%");
		stMap.put(MOD_EQ,"%=");
		stMap.put(NOT,"!");
		stMap.put(NOT_EQ2,"!==");
		stMap.put(NOT_EQ,"!=");
		stMap.put(NOT_EQ_QMARK,"!=?");
		stMap.put(OR2,"||");
		stMap.put(OR,"|");
		stMap.put(OR_EQ,"|=");
		stMap.put(PLUS2,"++");
		stMap.put(PLUS,"+");
		stMap.put(PLUS_COLON,"+:");
		stMap.put(PLUS_EQ,"+=");
		stMap.put(POUND2,"##");
		stMap.put(POUND,"#");
		stMap.put(POUND_EQ_POUND,"#=#");
		stMap.put(POUND_MINUS_POUND,"#-#");
		stMap.put(QMARK,"?");
		stMap.put(RBRACK,"]");
		stMap.put(RCURLY,"}");
		stMap.put(RPAREN,")");
		stMap.put(SEMI,";");
		stMap.put(STAR2,"**");
		stMap.put(STAR,"*");
		stMap.put(STAR_COLON2_STAR,"*::*");
		stMap.put(STAR_EQ,"*=");
		stMap.put(STAR_GT,"*>");
		stMap.put(STAR_RPAREN,"*)");
		stMap.put(TILDE,"~");
		stMap.put(TILDE_AND,"~&");
		stMap.put(TILDE_OR,"~|");
		stMap.put(TILDE_XOR,"~^");
		stMap.put(XOR,"^");
		stMap.put(XOR_EQ,"^=");
		stMap.put(XOR_TILDE,"^~");
		stMap.put(IDENT,"<IDENT>");
		stMap.put(ESC_IDENT,"<ESC_IDENT>");
		stMap.put(UNSIGNED_NUMBER,"<UNSIGNED_NUMBER>");
		stMap.put(UNBASED_UNSIZED_LITERAL,"<UNBASED_UNSIZED_LITERAL>");
		stMap.put(NUMBER,"<NUMBER>");
		stMap.put(TIME_LITERAL,"<TIME_LITERAL>");
		stMap.put(SYSTEM_IDENT,"<SYSTEM_IDENT>");
		stMap.put(SQUOTE,"'");
    	stMap.put(STRING_LITERAL,"<STRING_LITERAL>");
	}
%}

/* main character classes */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

Space = [ \t\f]
WhiteSpace = {LineTerminator} | {Space}

/* comments */
Comment = ("/*" ~"*/") | {EndOfLineComment}
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?

/* identifiers */
IDENT = [a-zA-Z_][a-zA-Z_0-9$]*
ESC_IDENT = \\ ~{WhiteSpace}
SYSTEM_IDENT = "$" {IDENT}

/*No whitespace between number and unit*/
TIME_LITERAL = ({UnsignedNumber} | {FixedPointNumber}) {TimeUnit}
TimeUnit = [munpf]? "s"

/*Used only in `timescale since allows whitespace between num and unit */
TIME_UNIT_OR_PRECISION = ({UnsignedNumber} | {FixedPointNumber}) {Space}* {TimeUnit}

TicLine = "`line" {Space}+ [0-9]+ {Space}+ \" [^\"]+ \" {Space}+ [0-2]
TimeScale = "`timescale" {Space}+ {TIME_UNIT_OR_PRECISION} {Space}* "/" {Space}* {TIME_UNIT_OR_PRECISION}

OctDigit = [0-7]
HexDigit = [0-9a-fA-F]
NonZeroUnsignedNumber = [1-9][_0-9]*
UnsignedNumber        = [0-9][_0-9]*
RealNumber = {UnsignedNumber} "." {UnsignedNumber} ([eE] [+-]? {UnsignedNumber})?
FixedPointNumber = {UnsignedNumber} "." {UnsignedNumber}
BinaryValue = [01xXzZ?][01xXzZ?_]* 
OctalValue = [0-7xXzZ?][0-7xXzZ?_]*
HexValue = [0-9a-fA-FxXzZ?][0-9a-fA-FxXzZ?_]*

DecimalBase = {Size}? \' [sS]? [dD] {Space}*
BinaryBase  = {Size}? \' [sS]? [bB] {Space}*
OctalBase   = {Size}? \' [sS]? [oO] {Space}*
HexBase     = {Size}? \' [sS]? [hH] {Space}*

UNBASED_UNSIZED_LITERAL = \' {Space}* [01xXzZ?]

//Size only matches if followed by '
Size = {NonZeroUnsignedNumber} {Space}*

//A.8.7 Numbers
UNSIGNED_NUMBER = {UnsignedNumber}
NUMBER = {IntegralNumber} | {RealNumber}

IntegralNumber = ({OctalNumber} | {BinaryNumber} | {HexNumber} | {DecimalNumber})

DecimalNumber = {DecimalBase}? ({UnsignedNumber} | ([xXzZ?] "_"*))
BinaryNumber  = {BinaryBase} {BinaryValue}
OctalNumber   = {OctalBase} {OctalValue}
HexNumber     = {HexBase} {HexValue}

/* string and character literals */
StringCharacter = [^\r\n\"\\]

%state STRING

%%

<YYINITIAL> {
    {WhiteSpace}+    |
	{Comment}        |
	"`celldefine"    |
	"`endcelldefine" |
    {TimeScale}   { /* ignore */ }

	//{insert 'create'
	"accept_on" {return create(ACCEPT_ON_K);}
	"alias" {return create(ALIAS_K);}
	"always_comb" {return create(ALWAYS_COMB_K);}
	"always_ff" {return create(ALWAYS_FF_K);}
	"always" {return create(ALWAYS_K);}
	"always_latch" {return create(ALWAYS_LATCH_K);}
	"and" {return create(AND_K);}
	"assert" {return create(ASSERT_K);}
	"assign" {return create(ASSIGN_K);}
	"assume" {return create(ASSUME_K);}
	"automatic" {return create(AUTOMATIC_K);}
	"before" {return create(BEFORE_K);}
	"begin" {return create(BEGIN_K);}
	"bind" {return create(BIND_K);}
	"binsof" {return create(BINSOF_K);}
	"bins" {return create(BINS_K);}
	"bit" {return create(BIT_K);}
	"break" {return create(BREAK_K);}
	"bufif0" {return create(BUFIF0_K);}
	"bufif1" {return create(BUFIF1_K);}
	"buf" {return create(BUF_K);}
	"byte" {return create(BYTE_K);}
	"casex" {return create(CASEX_K);}
	"casez" {return create(CASEZ_K);}
	"case" {return create(CASE_K);}
	"cell" {return create(CELL_K);}
	"chandle" {return create(CHANDLE_K);}
	"checker" {return create(CHECKER_K);}
	"class" {return create(CLASS_K);}
	"clocking" {return create(CLOCKING_K);}
	"cmos" {return create(CMOS_K);}
	"config" {return create(CONFIG_K);}
	"constraint" {return create(CONSTRAINT_K);}
	"const" {return create(CONST_K);}
	"context" {return create(CONTEXT_K);}
	"continue" {return create(CONTINUE_K);}
	"covergroup" {return create(COVERGROUP_K);}
	"coverpoint" {return create(COVERPOINT_K);}
	"cover" {return create(COVER_K);}
	"cross" {return create(CROSS_K);}
	"deassign" {return create(DEASSIGN_K);}
	"default" {return create(DEFAULT_K);}
	"defparam" {return create(DEFPARAM_K);}
	"design" {return create(DESIGN_K);}
	"disable" {return create(DISABLE_K);}
	"dist" {return create(DIST_K);}
	"do" {return create(DO_K);}
	"edge" {return create(EDGE_K);}
	"else" {return create(ELSE_K);}
	"endcase" {return create(ENDCASE_K);}
	"endchecker" {return create(ENDCHECKER_K);}
	"endclass" {return create(ENDCLASS_K);}
	"endclocking" {return create(ENDCLOCKING_K);}
	"endconfig" {return create(ENDCONFIG_K);}
	"endfunction" {return create(ENDFUNCTION_K);}
	"endgenerate" {return create(ENDGENERATE_K);}
	"endgroup" {return create(ENDGROUP_K);}
	"endinterface" {return create(ENDINTERFACE_K);}
	"endmodule" {return create(ENDMODULE_K);}
	"endpackage" {return create(ENDPACKAGE_K);}
	"endprimitive" {return create(ENDPRIMITIVE_K);}
	"endprogram" {return create(ENDPROGRAM_K);}
	"endproperty" {return create(ENDPROPERTY_K);}
	"endsequence" {return create(ENDSEQUENCE_K);}
	"endspecify" {return create(ENDSPECIFY_K);}
	"endtable" {return create(ENDTABLE_K);}
	"endtask" {return create(ENDTASK_K);}
	"end" {return create(END_K);}
	"enum" {return create(ENUM_K);}
	"eventually" {return create(EVENTUALLY_K);}
	"event" {return create(EVENT_K);}
	"expect" {return create(EXPECT_K);}
	"export" {return create(EXPORT_K);}
	"extends" {return create(EXTENDS_K);}
	"extern" {return create(EXTERN_K);}
	"final" {return create(FINAL_K);}
	"first_match" {return create(FIRST_MATCH_K);}
	"force" {return create(FORCE_K);}
	"foreach" {return create(FOREACH_K);}
	"forever" {return create(FOREVER_K);}
	"forkjoin" {return create(FORKJOIN_K);}
	"fork" {return create(FORK_K);}
	"for" {return create(FOR_K);}
	"function" {return create(FUNCTION_K);}
	"generate" {return create(GENERATE_K);}
	"genvar" {return create(GENVAR_K);}
	"global" {return create(GLOBAL_K);}
	"highz0" {return create(HIGHZ0_K);}
	"highz1" {return create(HIGHZ1_K);}
	"iff" {return create(IFF_K);}
	"ifnone" {return create(IFNONE_K);}
	"if" {return create(IF_K);}
	"ignore_bins" {return create(IGNORE_BINS_K);}
	"illegal_bins" {return create(ILLEGAL_BINS_K);}
	"implies" {return create(IMPLIES_K);}
	"import" {return create(IMPORT_K);}
	"incdir" {return create(INCDIR_K);}
	"include" {return create(INCLUDE_K);}
	"initial" {return create(INITIAL_K);}
	"inout" {return create(INOUT_K);}
	"input" {return create(INPUT_K);}
	"inside" {return create(INSIDE_K);}
	"instance" {return create(INSTANCE_K);}
	"integer" {return create(INTEGER_K);}
	"interface" {return create(INTERFACE_K);}
	"intersect" {return create(INTERSECT_K);}
	"int" {return create(INT_K);}
	"join_any" {return create(JOIN_ANY_K);}
	"join" {return create(JOIN_K);}
	"join_none" {return create(JOIN_NONE_K);}
	"large" {return create(LARGE_K);}
	"let" {return create(LET_K);}
	"liblist" {return create(LIBLIST_K);}
	"library" {return create(LIBRARY_K);}
	"localparam" {return create(LOCALPARAM_K);}
	"local" {return create(LOCAL_K);}
	"logic" {return create(LOGIC_K);}
	"longint" {return create(LONGINT_K);}
	"macromodule" {return create(MACROMODULE_K);}
	"matches" {return create(MATCHES_K);}
	"medium" {return create(MEDIUM_K);}
	"modport" {return create(MODPORT_K);}
	"module" {return create(MODULE_K);}
	"nand" {return create(NAND_K);}
	"negedge" {return create(NEGEDGE_K);}
	"new" {return create(NEW_K);}
	"nexttime" {return create(NEXTTIME_K);}
	"nmos" {return create(NMOS_K);}
	"nor" {return create(NOR_K);}
	"noshowcancelled" {return create(NOSHOWCANCELLED_K);}
	"notif0" {return create(NOTIF0_K);}
	"notif1" {return create(NOTIF1_K);}
	"not" {return create(NOT_K);}
	"null" {return create(NULL_K);}
	"or" {return create(OR_K);}
	"output" {return create(OUTPUT_K);}
	"package" {return create(PACKAGE_K);}
	"packed" {return create(PACKED_K);}
	"parameter" {return create(PARAMETER_K);}
	"pmos" {return create(PMOS_K);}
	"posedge" {return create(POSEDGE_K);}
	"primitive" {return create(PRIMITIVE_K);}
	"priority" {return create(PRIORITY_K);}
	"program" {return create(PROGRAM_K);}
	"property" {return create(PROPERTY_K);}
	"protected" {return create(PROTECTED_K);}
	"pull0" {return create(PULL0_K);}
	"pull1" {return create(PULL1_K);}
	"pulldown" {return create(PULLDOWN_K);}
	"pullup" {return create(PULLUP_K);}
	"pulsestyle_ondetect" {return create(PULSESTYLE_ONDETECT_K);}
	"pulsestyle_onevent" {return create(PULSESTYLE_ONEVENT_K);}
	"pure" {return create(PURE_K);}
	"randcase" {return create(RANDCASE_K);}
	"randc" {return create(RANDC_K);}
	"randsequence" {return create(RANDSEQUENCE_K);}
	"rand" {return create(RAND_K);}
	"randomize" {return create(RANDOMIZE_K);}
	"rcmos" {return create(RCMOS_K);}
	"realtime" {return create(REALTIME_K);}
	"real" {return create(REAL_K);}
	"ref" {return create(REF_K);}
	"reg" {return create(REG_K);}
	"reject_on" {return create(REJECT_ON_K);}
	"release" {return create(RELEASE_K);}
	"repeat" {return create(REPEAT_K);}
	"restrict" {return create(RESTRICT_K);}
	"return" {return create(RETURN_K);}
	"rnmos" {return create(RNMOS_K);}
	"rpmos" {return create(RPMOS_K);}
	"rtranif0" {return create(RTRANIF0_K);}
	"rtranif1" {return create(RTRANIF1_K);}
	"rtran" {return create(RTRAN_K);}
	"scalared" {return create(SCALARED_K);}
	"sequence" {return create(SEQUENCE_K);}
	"shortint" {return create(SHORTINT_K);}
	"shortreal" {return create(SHORTREAL_K);}
	"showcancelled" {return create(SHOWCANCELLED_K);}
	"signed" {return create(SIGNED_K);}
	"small" {return create(SMALL_K);}
	"solve" {return create(SOLVE_K);}
	"specify" {return create(SPECIFY_K);}
	"specparam" {return create(SPECPARAM_K);}
	"static" {return create(STATIC_K);}
	"std" {return create(STD_K);}
	"string" {return create(STRING_K);}
	"strong0" {return create(STRONG0_K);}
	"strong1" {return create(STRONG1_K);}
	"strong" {return create(STRONG_K);}
	"struct" {return create(STRUCT_K);}
	"super" {return create(SUPER_K);}
	"supply0" {return create(SUPPLY0_K);}
	"supply1" {return create(SUPPLY1_K);}
	"sync_accept_on" {return create(SYNC_ACCEPT_ON_K);}
	"sync_reject_on" {return create(SYNC_REJECT_ON_K);}
	"s_always" {return create(S_ALWAYS_K);}
	"s_eventually" {return create(S_EVENTUALLY_K);}
	"s_nexttime" {return create(S_NEXTTIME_K);}
	"s_until" {return create(S_UNTIL_K);}
	"s_until_with" {return create(S_UNTIL_WITH_K);}
	"table" {return create(TABLE_K);}
	"tagged" {return create(TAGGED_K);}
	"task" {return create(TASK_K);}
	"this" {return create(THIS_K);}
	"throughout" {return create(THROUGHOUT_K);}
	"timeprecision" {return create(TIMEPRECISION_K);}
	"timeunit" {return create(TIMEUNIT_K);}
	"time" {return create(TIME_K);}
	"tranif0" {return create(TRANIF0_K);}
	"tranif1" {return create(TRANIF1_K);}
	"tran" {return create(TRAN_K);}
	"tri0" {return create(TRI0_K);}
	"tri1" {return create(TRI1_K);}
	"triand" {return create(TRIAND_K);}
	"trior" {return create(TRIOR_K);}
	"trireg" {return create(TRIREG_K);}
	"tri" {return create(TRI_K);}
	"typedef" {return create(TYPEDEF_K);}
	"type" {return create(TYPE_K);}
	"union" {return create(UNION_K);}
	"unique0" {return create(UNIQUE0_K);}
	"unique" {return create(UNIQUE_K);}
	"unsigned" {return create(UNSIGNED_K);}
	"until" {return create(UNTIL_K);}
	"until_with" {return create(UNTIL_WITH_K);}
	"untyped" {return create(UNTYPED_K);}
	"use" {return create(USE_K);}
	"uwire" {return create(UWIRE_K);}
	"var" {return create(VAR_K);}
	"vectored" {return create(VECTORED_K);}
	"virtual" {return create(VIRTUAL_K);}
	"void" {return create(VOID_K);}
	"wait" {return create(WAIT_K);}
	"wait_order" {return create(WAIT_ORDER_K);}
	"wand" {return create(WAND_K);}
	"weak0" {return create(WEAK0_K);}
	"weak1" {return create(WEAK1_K);}
	"weak" {return create(WEAK_K);}
	"while" {return create(WHILE_K);}
	"wildcard" {return create(WILDCARD_K);}
	"wire" {return create(WIRE_K);}
	"within" {return create(WITHIN_K);}
	"with" {return create(WITH_K);}
	"wor" {return create(WOR_K);}
	"xnor" {return create(XNOR_K);}
	"xor" {return create(XOR_K);}
	"1step" {return create(W1STEP_K);}
	"PATHPULSE$" {return create(PATHPULSE_K);}
	"$setup" {return create(DS_SETUP_K);}
	"$removal" {return create(DS_REMOVAL_K);}
	"$period" {return create(DS_PERIOD_K);}
	"$recrem" {return create(DS_RECREM_K);}
	"$setuphold" {return create(DS_SETUPHOLD_K);}
	"$recovery" {return create(DS_RECOVERY_K);}
	"$hold" {return create(DS_HOLD_K);}
	"$width" {return create(DS_WIDTH_K);}
	"$skew" {return create(DS_SKEW_K);}
	"$fullskew" {return create(DS_FULLSKEW_K);}
	"$timeskew" {return create(DS_TIMESKEW_K);}
	"$nochange" {return create(DS_NOCHANGE_K);}
	"$fatal" {return create(DS_FATAL_K);}
	"$error" {return create(DS_ERROR_K);}
	"$warning" {return create(DS_WARNING_K);}
	"$info" {return create(DS_INFO_K);}
	"$unit" {return create(DS_UNIT_K);}
	"$root" {return create(DS_ROOT_K);}
	"&" {return create(AND);}
	"&&" {return create(AND2);}
	"&&&" {return create(AND3);}
	"&=" {return create(AND_EQ);}
	"@" {return create(AT);}
	"@@" {return create(AT2);}
	"@*" {return create(AT_STAR);}
	"|=>" {return create(BAR_EQ_GT);}
	"|->" {return create(BAR_MINUS_GT);}
	"::" {return create(COLON2);}
	":" {return create(COLON);}

	//match :/ only if not ://
	":/" / [^/] {return create(COLON_DIV);}

	":=" {return create(COLON_EQ);}
	"," {return create(COMMA);}
	"/" {return create(DIV);}
	"/=" {return create(DIV_EQ);}
	"$" {return create(DOLLAR);}
	"." {return create(DOT);}
	".*" {return create(DOT_STAR);}
	"==" {return create(EQ2);}
	"==?" {return create(EQ2_QMARK);}
	"===" {return create(EQ3);}
	"=" {return create(EQ);}
	"=>" {return create(EQ_GT);}
	">>" {return create(GT2);}
	">>=" {return create(GT2_EQ);}
	">>>=" {return create(GT3_EQ);}
	">" {return create(GT);}
	">>>" {return create(GT3);}
	">=" {return create(GT_EQ);}
	"[" {return create(LBRACK);}
	"{" {return create(LCURLY);}
	"(" {return create(LPAREN);}
	"(*" {return create(LPAREN_STAR);}
	"<<" {return create(LT2);}
	"<<=" {return create(LT2_EQ);}
	"<<<=" {return create(LT3_EQ);}
	"<" {return create(LT);}
	"<<<" {return create(LT3);}
	"<=" {return create(LT_EQ);}
	"<->" {return create(LT_MINUS_GT);}
	"--" {return create(MINUS2);}
	"-" {return create(MINUS);}
	"-:" {return create(MINUS_COLON);}
	"-=" {return create(MINUS_EQ);}
	"->>" {return create(MINUS_GT2);}
	"->" {return create(MINUS_GT);}
	"%" {return create(MOD);}
	"%=" {return create(MOD_EQ);}
	"!" {return create(NOT);}
	"!==" {return create(NOT_EQ2);}
	"!=" {return create(NOT_EQ);}
	"!=?" {return create(NOT_EQ_QMARK);}
	"||" {return create(OR2);}
	"|" {return create(OR);}
	"|=" {return create(OR_EQ);}
	"++" {return create(PLUS2);}
	"+" {return create(PLUS);}
	"+:" {return create(PLUS_COLON);}
	"+=" {return create(PLUS_EQ);}
	"##" {return create(POUND2);}
	"#" {return create(POUND);}
	"#=#" {return create(POUND_EQ_POUND);}
	"#-#" {return create(POUND_MINUS_POUND);}
	"?" {return create(QMARK);}
	"]" {return create(RBRACK);}
	"}" {return create(RCURLY);}
	")" {return create(RPAREN);}
	";" {return create(SEMI);}
	"**" {return create(STAR2);}
	"*" {return create(STAR);}
	"*::*" {return create(STAR_COLON2_STAR);}
	"*=" {return create(STAR_EQ);}
	"*>" {return create(STAR_GT);}
	"*)" {return create(STAR_RPAREN);}
	"~" {return create(TILDE);}
	"~&" {return create(TILDE_AND);}
	"~|" {return create(TILDE_OR);}
	"~^" {return create(TILDE_XOR);}
	"^" {return create(XOR);}
	"^=" {return create(XOR_EQ);}
	"^~" {return create(XOR_TILDE);}
	{IDENT} {return create(IDENT);}
	{ESC_IDENT} {return create(ESC_IDENT);}
	{UNSIGNED_NUMBER} {return create(UNSIGNED_NUMBER);}
	{UNBASED_UNSIZED_LITERAL} {return create(UNBASED_UNSIZED_LITERAL);}
	{NUMBER} {return create(NUMBER);}
	{TIME_LITERAL} {return create(TIME_LITERAL);}
	{SYSTEM_IDENT} {return create(SYSTEM_IDENT);}
	"'" {return create(SQUOTE);}
	//create}

  	{TicLine}	{ ticLine(); }

  /* string literal */
  \"                             { yybegin(STRING); string.setLength(0); }
}

<STRING> {
  \"                             { yybegin(YYINITIAL); return create(STRING_LITERAL, string.toString()); }
  
  {StringCharacter}+             { string.append( yytext() ); }
  
  /* escape sequences */
  "\\b"                          { string.append( '\b' ); }
  "\\t"                          { string.append( '\t' ); }
  "\\n"                          { string.append( '\n' ); }
  "\\f"                          { string.append( '\f' ); }
  "\\r"                          { string.append( '\r' ); }
  "\\\""                         { string.append( '\"' ); }
  "\\'"                          { string.append( '\'' ); }
  "\\\\"                         { string.append( '\\' ); }
  \\[0-3]?{OctDigit}?{OctDigit}  { char val = (char) Integer.parseInt(yytext().substring(1),8); string.append( val ); }
  \\h{HexDigit}?{HexDigit}  { char val = (char) Integer.parseInt(yytext().substring(1),16); string.append( val ); }
  
  /* error cases */
  \\.                            { error("Illegal escape sequence"); }
  {LineTerminator}               { error("Unterminated string at end of line"); }
}

/* error fallback */
[^]                              { error("Illegal character"); }
<<EOF>>                          { return create(Token.EOF);  }
