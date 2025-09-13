package com.rickynj.actions.netpretender;

import com.rickynj.domain.DevicesWrapper;
import com.rickynj.organisation.Organisation;
import com.rickynj.parser.YAMLParser;

public class NetPretenderTest {
    DevicesWrapper dw = YAMLParser.parseFile();
    Organisation org = new Organisation(dw);

}
