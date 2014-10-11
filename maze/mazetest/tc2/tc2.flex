package apfe.maze.tc2;
import apfe.maze.runtime.Scanner;
import apfe.maze.runtime.Token;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

%%

%public
%class Tc2Scanner
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
  	  	return new Token(stFileName, yyline+1, yycolumn+1, text, id);
  }

  private Token create(int id) {
  	  	return create(id, yytext());
  }

  private void ticLine() {
  	String ss = yytext();
  }

  public Tc2Scanner(String fn) throws FileNotFoundException {
	this(new FileReader(fn));
    setFileName(fn);
  }

  //TODO: derive from RuntimeException to pass back to parser
  private void error(String msg) {
      StringBuilder sb = new StringBuilder("Error: ");
      sb.append(getFileName()).append(':').append(yyline+1).append(':')
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
  
  public boolean isEOF() {
  	return zzAtEOF;
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
%%

    {WhiteSpace}+ |
    {Comment}   { /* ignore */ }

	//{insert 'create'
	"A" {return create(A_K);}
	"B" {return create(B_K);}
	"C" {return create(C_K);}
	"D" {return create(D_K);}

/* error fallback */
[^]                              { error("Illegal character"); }
<<EOF>>                          { return create(Token.EOF);  }
