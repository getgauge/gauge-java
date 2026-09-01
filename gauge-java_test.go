package main

import (
	"reflect"
	"testing"
)

func TestJavacUserArgsSplitsOnWhitespace(t *testing.T) {
	t.Setenv(javacArgsEnv, "-g:lines,source -parameters")
	got := javacUserArgs()
	want := []string{"-g:lines,source", "-parameters"}
	if !reflect.DeepEqual(got, want) {
		t.Fatalf("javacUserArgs() = %v, want %v", got, want)
	}
}

func TestJavacUserArgsEmpty(t *testing.T) {
	t.Setenv(javacArgsEnv, "")
	got := javacUserArgs()
	if len(got) != 0 {
		t.Fatalf("javacUserArgs() = %v, want empty", got)
	}
}
