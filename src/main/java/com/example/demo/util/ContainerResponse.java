package com.example.demo.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ContainerResponse<T> {

  private int code;

  private Collection<T> data;

  public ContainerResponse(int code, Collection<T> data) {
    this.code = code;
    this.data = data;
  }

  public ContainerResponse(int code) {
    this.code = code;
  }
}
