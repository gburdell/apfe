PEG  ?= expr.peg
TOOL ?= /home/gburdell/projects/apfe/bin/generate
DEPS ?= base.java.tmpl dlrbase.java.tmpl generate.props.txt ${PEG}

.PHONY: goal clean
goal: generate.log ${FLEX_GOAL}

generate.log: ${DEPS}
	-rm -f generate.log
	${TOOL} ${PEG} generate.props.txt > generate.log
	tail generate.log

clean:
	-rm -rf src/apfe/peg/expr/generated
	-rm -f generate.log
