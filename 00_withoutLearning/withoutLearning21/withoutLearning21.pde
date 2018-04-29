PFont HIRAGINO10;
PFont HIRAGINO20;
Neuron NEURON_0_0;
Neuron NEURON_0_1;
Neuron NEURON_1_0;
float MAX = 20;
float SCALE = 1;
color COLOR_TRUE = #CCFF90;
color COLOR_FALSE = #FF8A80;
String MOUSE_X_ASSIGN = "P00 undefined";
String MOUSE_Y_ASSIGN = "P00 undefined";
int MOUSE_MODE = 0;

void setup() {
  size(500, 500, P2D);
  SCALE = width / MAX;
  background(255);
  smooth();
  HIRAGINO10 = loadFont("HiraginoSans-W0-10.vlw");
  HIRAGINO20 = loadFont("HiraginoSans-W0-20.vlw");

  NEURON_0_0 = new Neuron(0.3, -2.2, -1.4);
  NEURON_0_1 = new Neuron(-0.4, -0.1, 0.2);
  NEURON_1_0 = new Neuron(-0.2, 0.5, -0.2);
}

void draw() {
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

void drawNeuron(float x1, float x2, float size) {
  float p00 = NEURON_0_0.run(x1, x2);
  float p01 = NEURON_0_1.run(x1, x2);
  // if (NEURON_0_0.run(x1, x2) > 0) { p00 = 1; }
  // float p01 = 0;
  // if (NEURON_0_1.run(x1, x2) > 0) { p01 = 1; }
  float p10 = NEURON_1_0.run(p00, p01);
  if (p10 > 0.5) {
    fill(COLOR_TRUE);
  } else {
    fill(COLOR_FALSE);
  }
  ellipse(x1 * SCALE, -x2 * SCALE, size, size);
}

void drawAxis() {
  stroke(150);
  line(-width / 2, 0, width / 2, 0);
  line(0, -height / 2, 0, height / 2);
  textFont(HIRAGINO20);
  textSize(20);
  fill(100);
  text("x1", width / 2 - 30, -5);
  text("x2", 5, -height / 2 + 30);
}

void drawParameters() {
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

void mouseMoved() {
  switch (MOUSE_X_ASSIGN) {
    case "P00 weight1":
      NEURON_0_0.weight1(5 * (mouseX - width / 2) / float(width));
      break;
    case "P01 weight1":
      NEURON_0_1.weight1(5 * (mouseX - width / 2) / float(width));
      break;
    case "P10 weight1":
      NEURON_1_0.weight1(5 * (mouseX - width / 2) / float(width));
      break;
    case "P00 bias":
      NEURON_0_0.bias(5 * (mouseX - width / 2) / float(width));
      break;
    case "P01 bias":
      NEURON_0_1.bias(5 * (mouseX - width / 2) / float(width));
      break;
    case "P10 bias":
      NEURON_1_0.bias(5 * (mouseX - width / 2) / float(width));
      break;
    default:
      break;
  }
  switch (MOUSE_Y_ASSIGN) {
    case "P00 weight2":
      NEURON_0_0.weight2(-5 * (mouseY - height / 2) / float(height));
      break;
    case "P01 weight2":
      NEURON_0_1.weight2(-5 * (mouseY - height / 2) / float(height));
      break;
    case "P10 weight2":
      NEURON_1_0.weight2(-5 * (mouseY - height / 2) / float(height));
      break;
    default:
      break;
  }
}

void keyPressed() {
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
