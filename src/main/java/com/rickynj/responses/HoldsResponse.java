package com.rickynj.responses;

import java.util.List;

public interface HoldsResponse {
  String getResponse();
  List<String> getMultipartResponse();
  String getResponseFile();
  int getDelay();
}
