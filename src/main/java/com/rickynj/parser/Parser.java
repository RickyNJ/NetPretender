package com.rickynj.parser;

import com.rickynj.device.Device;

public interface Parser {
    Device parse(java.io.File input);
}
