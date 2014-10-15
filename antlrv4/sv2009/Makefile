ANTLR ?= antlr4
GRAM ?= Sv2009.g4
OPTS ?= -no-listener -no-visitor -package antlr4.sv2009 -o src/antlr4/sv2009

default: ${GRAM}
	${ANTLR} ${OPTS} ${GRAM}
