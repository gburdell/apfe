PEG  ?= tc1.peg
TOOL ?= /home/gburdell/projects/apfe/bin/generate
DEPS ?= base.java.tmpl dlrbase.java.tmpl generate.props.txt ${PEG}

FLEX_DIR ?= src/apfe/tc1
FLEX_GOAL ?= ${FLEX_DIR}/SvScanner.java

.PHONY: goal clean
goal: generate.log ${FLEX_GOAL}

generate.log: ${DEPS}
	-rm -f generate.log
	${TOOL} ${PEG} generate.props.txt > generate.log
	tail generate.log

${FLEX_GOAL}: tc1.flex
	jflex -d $(dir ${FLEX_GOAL}) --nobak ${<}

clean:
	-rm -rf src/apfe/tc1/generated
	-rm -f generate.log
	-rm -f ${FLEX_GOAL}
