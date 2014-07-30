package com.thoughtworks.gauge;

import com.google.protobuf.CodedInputStream;

class MessageLength {
        public long length;
        public CodedInputStream remainingStream;

        public MessageLength(long length, CodedInputStream remainingStream) {
            this.length = length;
            this.remainingStream = remainingStream;
        }
    }