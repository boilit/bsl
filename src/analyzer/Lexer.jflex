// Copyright (c) 2013, Boilit. All Rights Reserved.

package org.boilit.bsl.core;

import org.boilit.cup.Symbol;
import org.boilit.cup.Scanner;

%%
%public
%class Lexer
%implements ITokens, Scanner

%function next_token
%type Symbol
%line
%column
%unicode

%{
	public boolean holded = false;
	public char wrapper = 0;
	private int line = 0;
	private int column = 0;
	private LexerBuffer buffer = new LexerBuffer(256);
	
	public int getLine() {
		return yyline + 1;
	}
	
	public int getColumn() {
		return yycolumn + 1;
	}

    public final String yytext(int endOffset) {
        return new String(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead + endOffset);
    }

    public final String yytext(int startOffset, int endOffset) {
        return new String(zzBuffer, zzStartRead + startOffset, zzMarkedPos - zzStartRead + endOffset);
    }
	
	private void append() {
		buffer.append(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead);
	}
	
	private void append(char c) {
		buffer.append(c);
	}
	
	private void append(char c, int repeat) {
		if(repeat == 1) {
			buffer.append(c);
			return;
		}
		char[] chars = new char[repeat];
		for(int i=0; i < repeat; i++) { 
			chars[i] = c;
		}
		buffer.append(chars);
	}

	private void append(String string) {
		buffer.append(string);
	}

	private void delCodeLineEscape() {
		buffer.delCodeLineEscape();
	}
	
	private String pop() {
		String chars = buffer.toString();
		buffer.clear();
		return chars;
	}
	
	private void reset() {
		buffer.clear();
		line = yyline;
		column = yycolumn;
	}
	
	private Symbol symbol(int sym) {
		return new Symbol(sym, yyline + 1, yycolumn + 1, sym);
	}
	
	private Symbol symbol(int sym, Object value) {
		return new Symbol(sym, yyline + 1, yycolumn + 1, value);
	}
	
	private Symbol symbol(int sym, int line, int column, Object value) {
		return new Symbol(sym, line, column, value);
	}
%}

Line	 		= \r|\n|\r\n|\n\r
Null			= "null"
Bool			= "true" | "false"
Byte            = [0-9]+ [bB]
Short           = [0-9]+ [sS]
Integer         = [0-9]+
Long            = [0-9]+ [lL]
Float           = [0-9]+ ("." [0-9]+)? ([eE] [\+-]? [0-9]+)? [fF]
Double          = [0-9]+ ("." [0-9]+)? ([eE] [\+-]? [0-9]+)? [dD]
Label			= [$_a-zA-Z] [$_0-9a-zA-Z]*
Blank	 		= {Line} | [ \t\f]
Notes			= "/*" .* "*/" | "//" [^\r\n]* \r? \n

CodeHead 		= "<!--["
CodeTail 		= "]-->"
HoldHead 		= "${"
// HoldTail		= "}"
MatchCodeHead	= [\\]* {CodeHead}
MatchHoldHead	= [\\]* {HoldHead}

%state YYSTATEMENT, STRING, END_OF_FILE

%%

/* InputText Analyser */
<YYINITIAL>{
	/* CodeHead Matched */
	{MatchCodeHead} {
		int n = yylength() -5;
		append('\\',n/2);
		if(n%2 == 0){
		    holded = false;
		    delCodeLineEscape();
		    yybegin(YYSTATEMENT);
		    return symbol(TEXT, line, column, pop());
		} else {
		    append("<!--[");
		}
	}

	/* HoldHead Matched */
	{MatchHoldHead} {
		int n = yylength()-2;
		append('\\',n/2);
		if(n%2 == 0){
		    holded = true;
		    yybegin(YYSTATEMENT);
		    return symbol(TEXT, line, column, pop());
		} else {
		    append("${");
		}
	}

	.|{Line} {
		append();
	}

	<<EOF>> {
		yybegin(END_OF_FILE);
		return symbol(TEXT, line, column, pop());
	}
}

<YYSTATEMENT> {

	"include" { return symbol(INCLUDE); }
	"echo" { return symbol(ECHO); }
	"arg" { return symbol(ARG); }
	"var" { return symbol(VAR); }
	"if" { return symbol(IF); }
	"else" { return symbol(ELSE); }
	"loop" { return symbol(LOOP); }
	"next" { return symbol(NEXT); }
	"break" { return symbol(BREAK); }
	"bpw" { return symbol(BPW); }
	"pow" { return symbol(NUM_POW); }
	"merge" { return symbol(MERGE); }
	"#" { return symbol(WELL); } // frag define
	"@" { return symbol(AT); } // frag exec

	"." { return symbol(DOT); }
	":" { return symbol(COLON); }
	"," { return symbol(COMMA); }
	";" { return symbol(SEMI); }

	"?" { return symbol(QUESTION); }
	"?:" { return symbol(QUESTION_COLON); }

	"&&" { return symbol(LGC_AND); }
	"||" { return symbol(LGC_OR); }
	"!" { return symbol(LGC_NOT); }

	"<" { return symbol(LGC_CLT); }
	">" { return symbol(LGC_CGT); }
	"<=" { return symbol(LGC_CLE); }
	">=" { return symbol(LGC_CGE); }
	"==" { return symbol(LGC_CEE); }
	"!=" { return symbol(LGC_CNE); }

	"+" { return symbol(NUM_ADD); }
	"-" { return symbol(NUM_SUB); }
	"*" { return symbol(NUM_MUL); }
	"/" { return symbol(NUM_DIV); }
	"%" { return symbol(NUM_MOD); }
	"++" { return symbol(NUM_ADD1); }
	"--" { return symbol(NUM_SUB1); }

	"&" { return symbol(BIT_AND); }
	"|" { return symbol(BIT_OR); }
	"^" { return symbol(BIT_XOR); }
	"<<" { return symbol(BIT_LM); }
	">>" { return symbol(BIT_RM); }
	">>>" { return symbol(BIT_ZRM); }
	"~" { return symbol(BIT_NOT); }

	"=" { return symbol(ASSIGN); }
	"&&=" { return symbol(LGC_AND_ASSIGN); }
	"||=" { return symbol(LGC_OR_ASSIGN); }

	"+=" { return symbol(NUM_ADD_ASSIGN); }
	"-=" { return symbol(NUM_SUB_ASSIGN); }
	"*=" { return symbol(NUM_MUL_ASSIGN); }
	"/=" { return symbol(NUM_DIV_ASSIGN); }
	"%=" { return symbol(NUM_MOD_ASSIGN); }

	"&=" { return symbol(BIT_AND_ASSIGN); }
	"|=" { return symbol(BIT_OR_ASSIGN); }
	"^=" { return symbol(BIT_XOR_ASSIGN); }
	"<<=" { return symbol(BIT_LM_ASSIGN); }
	">>=" { return symbol(BIT_RM_ASSIGN); }
	">>>=" { return symbol(BIT_ZRM_ASSIGN); }

	"{" { return symbol(BRC_LEFT); }
	"}" { if(!holded) {return symbol(BRC_RIGHT);} else { yybegin(YYINITIAL); holded=false; return symbol(HOLD_TAIL); } }
	"[" { return symbol(BRK_LEFT); }
	"]" { return symbol(BRK_RIGHT); }
	"(" { return symbol(PRN_LEFT); }
	")" { return symbol(PRN_RIGHT); }

	"\"" {
	    wrapper='"';
	    yybegin(STRING);
	    reset();
	}
	"'" {
	    wrapper='\'';
	    yybegin(STRING);
	    reset();
	}
	
	{Notes} { /* ignore */ }
 	{Blank} { /* ignore */ }

	{Null} { return symbol(NULL); }
	{Bool} { return symbol(BOOL, Boolean.parseBoolean(yytext())); }
	{Byte} { return symbol(BYTE, Byte.parseByte(yytext(-1))); }
	{Short} { return symbol(SHORT, Short.parseShort(yytext(-1))); }
	{Integer} { return symbol(INTEGER, Integer.parseInt(yytext())); }
	{Long} { return symbol(LONG, Long.parseLong(yytext(-1))); }
	{Float} { return symbol(FLOAT, Float.parseFloat(yytext(-1))); }
	{Double} { return symbol(DOUBLE, Double.parseDouble(yytext(-1))); }
	{Label} { return symbol(LABEL, yytext()); }

	/* CodeTail Matched then goto YYINITIAL */
	{CodeTail} { yybegin(YYINITIAL); }
}

<STRING>{
    [\\]*[\"\'\\] {
        int n = yylength()-1;
        append('\\',n/2);
        char c = yycharat(n);
        if(wrapper==c && n%2 == 0){
            yybegin(YYSTATEMENT);
            return symbol(CHARS, line, column, pop());
        } else {
            append(c);
        }
    }

    [^\"\'\\]+ { append(); }

    "\\b" { append('\b'); }
    "\\t" { append('\t'); }
    "\\n" { append('\n'); }
    "\\f" { append('\f'); }
    "\\r" { append('\r'); }
    \\[0-3]?[0-7]?[0-7] { append((char) Integer.parseInt(yytext(1,0),8));  }

    \\. { throw new RuntimeException("Illegal escape sequence \"" + yytext() + "\""); }
}

<END_OF_FILE>{
    <<EOF>> { return symbol(EOF); }
}

/* Error FallBack */
.|\n { 
    throw new RuntimeException("Illegal character \"" + yytext() + "\" at line " + yyline + ", column " + yycolumn);
}

<<EOF>> { 
	return symbol(EOF); 
}
