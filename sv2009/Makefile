PEG  ?= sv2009.peg
TOOL ?= /home/gburdell/projects/apfe/bin/generate
DEPS ?= base.java.tmpl dlrbase.java.tmpl generate.props.txt ${PEG}

.PHONY: goal clean
goal:
	-rm -f generate.log
	${TOOL} ${PEG} generate.props.txt > generate.log
	tail generate.log

clean:
	-rm -rf src/apfe/sv2009/generated
