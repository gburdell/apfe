FLEX ?= jflex
TARGET1 ?= Tc1Scanner.java
TARGET2 ?= generated/Grammar.java
GENERATE ?= /home/gburdell/projects/apfe/bin/generate

.PHONY: all
all: ${TARGET1} ${TARGET2} ;

${TARGET}: tc1.flex
	${FLEX} --nobak ${<}

.PHONY: clean
clean:
	-rm -f ${TARGET}
	-rm -rf generated

${TARGET2}: tc1.peg base.java.tmpl generate.props.txt
	${GENERATE} tc1.peg generate.props.txt > generate.log
	tail generate.log
