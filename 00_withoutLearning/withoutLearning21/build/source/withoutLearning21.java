import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class withoutLearning21 extends PApplet {

PFont HIRAGINO10;
PFont HIRAGINO20;
Neuron NEURON_0_0;
Neuron NEURON_0_1;
Neuron NEURON_1_0;
float MAX = 20;
float SCALE = 1;
int COLOR_TRUE = 0xffCCFF90;
int COLOR_FALSE = 0xffFF8A80;
String MOUSE_X_ASSIGN = "P00 undefined";
String MOUSE_Y_ASSIGN = "P00 undefined";
int MOUSE_MODE = 0;

public void setup() {
  
  SCALE = width / MAX;
  background(255);
  
  HIRAGINO10 = loadFont("HiraginoSans-W0-10.vlw");
  HIRAGINO20 = loadFont("HiraginoSans-W0-20.vlw");

  NEURON_0_0 = new Neuron(0.3f, -2.2f, -1.4f);
  NEURON_0_1 = new Neuron(-0.4f, -0.1f, 0.2f);
  NEURON_1_0 = new Neuron(-0.2f, 0.5f, -0.2f);

  setupUi();
}

public void update() {
  updateParameters();
}

public void draw() {
  update();
  pushMatrix(); {
    translate(width / 2, height / 2);
    for (int i = 0; i < 10000; i++) {
      float x1 = random(-MAX, MAX);
      float x2 = random(-MAX, MAX);
      noStroke();
      drawNeuron(x1, x2, 4);
    }
    drawAxis();
    stroke(150);
    strokeWeight(1);
    drawNeuron(0, 0, 15);
    drawNeuron(0, 1, 15);
    drawNeuron(1, 0, 15);
    drawNeuron(1, 1, 15);
  } popMatrix();
}

public void drawNeuron(float x1, float x2, float size) {
  float p00 = NEURON_0_0.run(x1, x2);
  float p01 = NEURON_0_1.run(x1, x2);
  float p10 = NEURON_1_0.run(p00, p01);
  if (p10 > 0.5f) {
    fill(COLOR_TRUE);
  } else {
    fill(COLOR_FALSE);
  }
  ellipse(x1 * SCALE, -x2 * SCALE, size, size);
}

public void updateParameters() {
  NEURON_0_0.weight1(CP5.getController("P00_WEIGHT1").getValue());
  NEURON_0_0.weight2(CP5.getController("P00_WEIGHT2").getValue());
  NEURON_0_0.bias(CP5.getController("P00_BIAS").getValue());
  NEURON_0_1.weight1(CP5.getController("P01_WEIGHT1").getValue());
  NEURON_0_1.weight2(CP5.getController("P01_WEIGHT2").getValue());
  NEURON_0_1.bias(CP5.getController("P01_BIAS").getValue());
  NEURON_1_0.weight1(CP5.getController("P10_WEIGHT1").getValue());
  NEURON_1_0.weight2(CP5.getController("P10_WEIGHT2").getValue());
  NEURON_1_0.bias(CP5.getController("P10_BIAS").getValue());
}

public void keyPressed() {
  switch (key) {
    case ' ':
      background(255);
      break;
    default:
      break;
  }
}
class Neuron {
  private float weight1;
  private float weight2;
  private float bias;
  Neuron(float weight1, float weight2, float bias) {
    this.weight1 = weight1;
    this.weight2 = weight2;
    this.bias = bias;
  }

  public float run(float x1, float x2) {
    return sigmoid(weight1 * x1 + weight2 * x2 + bias);
  }

  public float sigmoid(float x) {
    return 1.0f / (1.0f + exp(-x));
  }

  public void weight1(float weight1) { this.weight1 = weight1; }
  public void weight2(float weight2) { this.weight2 = weight2; }
  public void bias(float bias) { this.bias = bias; }
  public float weight1() { return weight1; }
  public float weight2() { return weight2; }
  public float bias() { return bias; }
}


ControlP5 CP5;

int sliderWidth = 100;
int sliderHeight = 15;
int rangeMin = -10;
int rangeMax = 10;

public void setupUi() {
  int sliderPositionY = 10;
  CP5 = new ControlP5(this);
  CP5.setColorCaptionLabel(color(100));
  CP5.addSlider("P00_WEIGHT1")
    .setLabel("P00 weight1")
    .setPosition(10, sliderPositionY)
    .setSize(sliderWidth, sliderHeight)
    .setRange(rangeMin, rangeMax);
  sliderPositionY += 20;
  CP5.addSlider("P00_WEIGHT2")
    .setLabel("P00 weight2")
    .setPosition(10, sliderPositionY)
    .setSize(sliderWidth, sliderHeight)
    .setRange(rangeMin, rangeMax);
  sliderPositionY += 20;
  CP5.addSlider("P00_BIAS")
    .setLabel("P00 bias")
    .setPosition(10, sliderPositionY)
    .setSize(sliderWidth, sliderHeight)
    .setRange(rangeMin, rangeMax);
  sliderPositionY += 20;
  CP5.addSlider("P01_WEIGHT1")
    .setLabel("P01 weight1")
    .setPosition(10, sliderPositionY)
    .setSize(sliderWidth, sliderHeight)
    .setRange(rangeMin, rangeMax);
  sliderPositionY += 20;
  CP5.addSlider("P01_WEIGHT2")
    .setLabel("P01 weight2")
    .setPosition(10, sliderPositionY)
    .setSize(sliderWidth, sliderHeight)
    .setRange(rangeMin, rangeMax);
  sliderPositionY += 20;
  CP5.addSlider("P01_BIAS")
    .setLabel("P01 bias")
    .setPosition(10, sliderPositionY)
    .setSize(sliderWidth, sliderHeight)
    .setRange(rangeMin, rangeMax);
  sliderPositionY += 20;
  CP5.addSlider("P10_WEIGHT1")
    .setLabel("P10 weight1")
    .setPosition(10, sliderPositionY)
    .setSize(sliderWidth, sliderHeight)
    .setRange(rangeMin, rangeMax);
  sliderPositionY += 20;
  CP5.addSlider("P10_WEIGHT2")
    .setLabel("P10 weight2")
    .setPosition(10, sliderPositionY)
    .setSize(sliderWidth, sliderHeight)
    .setRange(rangeMin, rangeMax);
  sliderPositionY += 20;
  CP5.addSlider("P10_BIAS")
    .setLabel("P10 bias")
    .setPosition(10, sliderPositionY)
    .setSize(sliderWidth, sliderHeight)
    .setRange(rangeMin, rangeMax);
  sliderPositionY += 20;

  setValue();
}

public void setValue() {
  CP5.getController("P00_WEIGHT1").setValue(NEURON_0_0.weight1());
  CP5.getController("P00_WEIGHT2").setValue(NEURON_0_0.weight2());
  CP5.getController("P00_BIAS").setValue(NEURON_0_0.bias());
  CP5.getController("P01_WEIGHT1").setValue(NEURON_0_1.weight1());
  CP5.getController("P01_WEIGHT2").setValue(NEURON_0_1.weight2());
  CP5.getController("P01_BIAS").setValue(NEURON_0_1.bias());
  CP5.getController("P10_WEIGHT1").setValue(NEURON_1_0.weight1());
  CP5.getController("P10_WEIGHT2").setValue(NEURON_1_0.weight2());
  CP5.getController("P10_BIAS").setValue(NEURON_1_0.bias());
}

public void drawAxis() {
  stroke(150);
  line(-width / 2, 0, width / 2, 0);
  line(0, -height / 2, 0, height / 2);
  textFont(HIRAGINO20);
  textSize(20);
  fill(100);
  text("x1", width / 2 - 30, -5);
  text("x2", 5, -height / 2 + 30);
}
  public void settings() {  size(1000, 1000, P2D);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "withoutLearning21" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
