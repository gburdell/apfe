FLEX ?= jflex
TARGET1 ?= SvScanner.java
TARGET2 ?= generated/Grammar.java
GENERATE ?= /home/gburdell/projects/apfe/bin/generate

.PHONY: all
all: ${TARGET1} ${TARGET2} ;

${TARGET1}: sv2009.flex
	${FLEX} --nobak ${<}

.PHONY: clean
clean:
	-rm -f ${TARGET1}
	-rm -f generate.log
	-rm -rf generated

${TARGET2}: sv2009.peg base.java.tmpl generate.props.txt
	-rm -f generate.log
	${GENERATE} sv2009.peg generate.props.txt > generate.log
	tail generate.log
