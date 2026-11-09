package com.mrsun.ironchests.client.model;

public class ModelItem {

  private final float x;
  private final float y;
  private final float z;
  private final float size;

  public ModelItem(float x, float y, float z, float size) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.size = size;
  }

  public float getX() {
    return this.x;
  }

  public float getY() {
    return this.y;
  }

  public float getZ() {
    return this.z;
  }

  public float getSize() {
    return this.size;
  }

  public float getSizeScaled() {
    return this.size * 0.1F;
  }
}
