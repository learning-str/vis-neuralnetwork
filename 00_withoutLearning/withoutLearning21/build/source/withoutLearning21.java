import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

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
}

public void draw() {
  translate(width / 2, height / 2);
  for (int i = 0; i < 10000; i++) {
    float x1 = random(-MAX, MAX);
    float x2 = random(-MAX, MAX);
    noStroke();
    drawNeuron(x1, x2, 2);
  }
  drawAxis();
  stroke(150);
  strokeWeight(1);
  drawNeuron(0, 0, 15);
  drawNeuron(0, 1, 15);
  drawNeuron(1, 0, 15);
  drawNeuron(1, 1, 15);
  drawParameters();
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

public void drawParameters() {
  translate(-width / 2, -height / 2);
  textFont(HIRAGINO10);
  textSize(10);
  fill(100);
  text("P00 weight1: " + NEURON_0_0.weight1(), 5, 20);
  text("P00 weight2: " + NEURON_0_0.weight2(), 5, 35);
  text("P00 bias: " + NEURON_0_0.bias(), 5, 50);
  text("P01 weight1: " + NEURON_0_1.weight1(), 5, 65);
  text("P01 weight2: " + NEURON_0_1.weight2(), 5, 80);
  text("P01 bias: " + NEURON_0_1.bias(), 5, 95);
  text("P10 weight1: " + NEURON_1_0.weight1(), 5, 110);
  text("P10 weight2: " + NEURON_1_0.weight2(), 5, 125);
  text("P10 bias: " + NEURON_1_0.bias(), 5, 140);
  text("mouseX assign: " + MOUSE_X_ASSIGN, 5, 160);
  text("mouseY assign: " + MOUSE_Y_ASSIGN, 5, 175);
}

public void mouseMoved() {
  switch (MOUSE_X_ASSIGN) {
    case "P00 weight1":
      NEURON_0_0.weight1(5 * (mouseX - width / 2) / PApplet.parseFloat(width));
      break;
    case "P01 weight1":
      NEURON_0_1.weight1(5 * (mouseX - width / 2) / PApplet.parseFloat(width));
      break;
    case "P10 weight1":
      NEURON_1_0.weight1(5 * (mouseX - width / 2) / PApplet.parseFloat(width));
      break;
    case "P00 bias":
      NEURON_0_0.bias(5 * (mouseX - width / 2) / PApplet.parseFloat(width));
      break;
    case "P01 bias":
      NEURON_0_1.bias(5 * (mouseX - width / 2) / PApplet.parseFloat(width));
      break;
    case "P10 bias":
      NEURON_1_0.bias(5 * (mouseX - width / 2) / PApplet.parseFloat(width));
      break;
    default:
      break;
  }
  switch (MOUSE_Y_ASSIGN) {
    case "P00 weight2":
      NEURON_0_0.weight2(-5 * (mouseY - height / 2) / PApplet.parseFloat(height));
      break;
    case "P01 weight2":
      NEURON_0_1.weight2(-5 * (mouseY - height / 2) / PApplet.parseFloat(height));
      break;
    case "P10 weight2":
      NEURON_1_0.weight2(-5 * (mouseY - height / 2) / PApplet.parseFloat(height));
      break;
    default:
      break;
  }
}

public void keyPressed() {
  switch (key) {
    case ' ':
      background(255);
      break;
    case 'c':
      if (MOUSE_MODE == 0) {
        MOUSE_X_ASSIGN = "P00 weight1";
        MOUSE_Y_ASSIGN = "P00 weight2";
        MOUSE_MODE = 1;
      } else if (MOUSE_MODE == 1) {
        MOUSE_X_ASSIGN = "P00 bias";
        MOUSE_Y_ASSIGN = "P00 undefined";
        MOUSE_MODE = 2;
      } else if (MOUSE_MODE == 2) {
        MOUSE_X_ASSIGN = "P01 weight1";
        MOUSE_Y_ASSIGN = "P01 weight2";
        MOUSE_MODE = 3;
      } else if (MOUSE_MODE == 3) {
        MOUSE_X_ASSIGN = "P01 bias";
        MOUSE_Y_ASSIGN = "P01 undefined";
        MOUSE_MODE = 4;
      } else if (MOUSE_MODE == 4) {
        MOUSE_X_ASSIGN = "P10 weight1";
        MOUSE_Y_ASSIGN = "P10 weight2";
        MOUSE_MODE = 5;
      } else if (MOUSE_MODE == 5) {
        MOUSE_X_ASSIGN = "P10 bias";
        MOUSE_Y_ASSIGN = "P10 undefined";
        MOUSE_MODE = 6;
      } else if (MOUSE_MODE == 6) {
        MOUSE_X_ASSIGN = "P00 undefined";
        MOUSE_Y_ASSIGN = "P00 undefined";
        MOUSE_MODE = 0;
      }
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
  public void settings() {  size(500, 500, P2D);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "withoutLearning21" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
