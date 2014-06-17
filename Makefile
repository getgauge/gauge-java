
ifndef prefix
prefix=/usr/local
endif

PROGRAM_NAME=gauge

default: clean
	export GOPATH=$(GOPATH):`pwd` && cd src && go build && cp src ../$(PROGRAM_NAME)-java
	ant jar

clean:
	ant clean

install:
	install -m 755 -d $(prefix)/lib/$(PROGRAM_NAME)/java
	install -m 755 -d $(prefix)/lib/$(PROGRAM_NAME)/java/libs
	install -m 644 libs/* $(prefix)/lib/$(PROGRAM_NAME)/java/libs
	install -m 644 build/jar/* $(prefix)/lib/$(PROGRAM_NAME)/java
	install -m 755 -d $(prefix)/bin
	install -m 755 $(PROGRAM_NAME)-java $(prefix)/bin
	install -m 755 -d $(prefix)/share/$(PROGRAM_NAME)/languages
	install -m 644 java.json $(prefix)/share/$(PROGRAM_NAME)/languages
	install -m 755 -d $(prefix)/share/$(PROGRAM_NAME)/skel/java
	install -m 755 -d $(prefix)/share/$(PROGRAM_NAME)/skel/env
	install -m 644 skel/StepImplementation.java $(prefix)/share/$(PROGRAM_NAME)/skel/java
	install -m 644 skel/java.properties $(prefix)/share/$(PROGRAM_NAME)/skel/env
