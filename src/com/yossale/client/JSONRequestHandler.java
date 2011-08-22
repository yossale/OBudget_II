package com.yossale.client;

import com.google.gwt.core.client.JavaScriptObject;

public interface JSONRequestHandler 
{
  public void onRequestComplete(JavaScriptObject json, String jsonString);
}