FLEX ?= jflex
TARGET1 ?= Tc2Scanner.java
TARGET2 ?= generated/Grammar.java
GENERATE ?= /home/gburdell/projects/apfe/bin/generate

.PHONY: all
all: ${TARGET1} ${TARGET2} ;

${TARGET1}: tc2.flex
	${FLEX} --nobak ${<}

.PHONY: clean
clean:
	-rm -f ${TARGET1}
	-rm -rf generated

${TARGET2}: tc2.peg base.java.tmpl generate.props.txt
	${GENERATE} tc2.peg generate.props.txt > generate.log
	tail generate.log
