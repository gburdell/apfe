DEPS ?= base.java.tmpl dlrbase.java.tmpl generate.props.txt term.peg
TOOL ?= /home/gburdell/projects/apfe/bin/generate
GOAL ?= Term.java

${GOAL}: ${DEPS}
	${TOOL} term.peg generate.props.txt
