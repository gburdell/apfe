GRAMMAR ?= laol
PEG  ?= ${GRAMMAR}.peg
TOOL ?= ${HOME}/projects/apfe/bin/generate
DEPS ?= base.java.tmpl dlrbase.java.tmpl generate.props.txt ${PEG}

.PHONY: goal clean
goal: clean generate.log

generate.log: ${PEG}
	-rm -f generate.log
	${TOOL} ${PEG} generate.props.txt > generate.log
	tail generate.log

clean:
	-rm -rf src/apfe/${GRAMMAR}generated
	-rm -f generate.log
