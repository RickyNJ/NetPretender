package com.rickynj.domain.POJO;

import java.util.List;

public interface HoldsResponse {
  String getResponse();
  List<String> getMultipartResponse();
  String getResponseFile();
  int getDelay();
}
