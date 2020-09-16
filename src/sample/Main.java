package sample;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.geometry.Pos;



public class Main extends Application {
  private Pane root = new Pane();
  private double xOffset = 0;
  private double yOffset = 0;
  private Button button0 = new Button("0");
  private Button button1 = new Button("1");
  private Button button2 = new Button("2");
  private Button button3 = new Button("3");
  private Button button4 = new Button("4");
  private Button button5 = new Button("5");
  private Button button6 = new Button("6");
  private Button button7 = new Button("7");
  private Button button8 = new Button("8");
  private Button button9 = new Button("9");
  private Button add = new Button("+");
  private Button subtract = new Button("-");
  private Button multiply = new Button("×");
  private Button divide = new Button("/");
  private Button power = new Button("^");
  private Button equals = new Button("=");
  private Button clear = new Button("C");
  private Button backspace = new Button("←");
  private Button leftBracket = new Button("(");
  private Button rightBracket = new Button(")");
  private TextField textField = new TextField("");


  private void button0_clicked() {
    textField.setText(textField.getText() + "0");
  }

  private void button1_clicked() {
    textField.setText(textField.getText() + "1");
  }

  private void button2_clicked() { textField.setText(textField.getText() + "2"); }

  private void button3_clicked() { textField.setText(textField.getText() + "3"); }

  private void button4_clicked() { textField.setText(textField.getText() + "4"); }

  private void button5_clicked() { textField.setText(textField.getText() + "5"); }

  private void button6_clicked() { textField.setText(textField.getText() + "6"); }

  private void button7_clicked() { textField.setText(textField.getText() + "7"); }

  private void button8_clicked() { textField.setText(textField.getText() + "8"); }

  private void button9_clicked() { textField.setText(textField.getText() + "9"); }

  private void power_clicked() {
    String textFieldsText = textField.getText();
    if(!textField.getText().isEmpty() && textFieldsText.charAt(textFieldsText.length() - 1) != '+' && textFieldsText.charAt(textFieldsText.length() - 1) != '-' && textFieldsText.charAt(textFieldsText.length() - 1) != '/' && textFieldsText.charAt(textFieldsText.length() - 1) != '*' && textFieldsText.charAt(textFieldsText.length() - 1) != '^') {
      textField.setText(textField.getText() + "^");
    }
  }

  private void add_clicked() {
    String textFieldsText = textField.getText();
    if(!textField.getText().isEmpty() && textFieldsText.charAt(textFieldsText.length() - 1) != '+' && textFieldsText.charAt(textFieldsText.length() - 1) != '-' && textFieldsText.charAt(textFieldsText.length() - 1) != '/' && textFieldsText.charAt(textFieldsText.length() - 1) != '*' && textFieldsText.charAt(textFieldsText.length() - 1) != '^') {
      textField.setText(textField.getText() + "+");
    }
  }

  private void subtract_clicked() {
    String textFieldsText = textField.getText();
    if(!textField.getText().isEmpty() && textFieldsText.charAt(textFieldsText.length() - 1) != '+' && textFieldsText.charAt(textFieldsText.length() - 1) != '-' && textFieldsText.charAt(textFieldsText.length() - 1) != '/' && textFieldsText.charAt(textFieldsText.length() - 1) != '*' && textFieldsText.charAt(textFieldsText.length() - 1) != '^') {
      textField.setText(textField.getText() + "-");
    } else if(textField.getText().isEmpty()) {
      textField.setText(textField.getText() + "-");
    }
  }

  private void multiply_clicked() {
    String textFieldsText = textField.getText();
    if(!textField.getText().isEmpty() && textFieldsText.charAt(textFieldsText.length() - 1) != '+' && textFieldsText.charAt(textFieldsText.length() - 1) != '-' && textFieldsText.charAt(textFieldsText.length() - 1) != '/' && textFieldsText.charAt(textFieldsText.length() - 1) != '*' && textFieldsText.charAt(textFieldsText.length() - 1) != '^') {
      textField.setText(textField.getText() + "*");
    }
  }

  private void divide_clicked() {
    String textFieldsText = textField.getText();
    if(!textField.getText().isEmpty() && textFieldsText.charAt(textFieldsText.length() - 1) != '+' && textFieldsText.charAt(textFieldsText.length() - 1) != '-' && textFieldsText.charAt(textFieldsText.length() - 1) != '/' && textFieldsText.charAt(textFieldsText.length() - 1) != '*' && textFieldsText.charAt(textFieldsText.length() - 1) != '^') {
      textField.setText(textField.getText() + "/");
    }
  }

  private void equals_clicked() {
    String textFieldsText = textField.getText();
    if(!textField.getText().isEmpty() && textFieldsText.charAt(textFieldsText.length() - 1) != '+' && textFieldsText.charAt(textFieldsText.length() - 1) != '/' && textFieldsText.charAt(textFieldsText.length() - 1) != '-' && textFieldsText.charAt(textFieldsText.length() - 1) != '*' && textFieldsText.charAt(textFieldsText.length() - 1) != '^') {
      String result = Calculator.calculate(textField.getText());
      textField.setText(result);
    }
  }

  private void backspace_clicked() {
    String textFieldsText = textField.getText();
    if(!textFieldsText.isEmpty()) {
      textFieldsText = textField.getText().substring(0, textField.getText().length() - 1);
      textField.setText(textFieldsText);
    }
  }

  private void clear_clicked() { textField.setText(""); }

  private void rightBracket_clicked() { textField.setText((textField.getText() + ")")); }

  private void leftBracket_clicked() { textField.setText(textField.getText() + "("); }

  @Override
  public void start(Stage stage) {
    //Custom exit button
    Button exit = new Button("x");
    exit.setMaxHeight(30);
    exit.setMinHeight(30);
    exit.setMaxWidth(50);
    exit.setMinWidth(50);
    exit.setOnAction(event -> Platform.exit());
    exit.setTranslateX(347);
    exit.setTranslateY(0);

    //Defining size and location of the buttons and text field
    textField.setPrefSize(400, 100);
    textField.setTranslateX(1);
    textField.setTranslateY(50);

    button7.setPrefSize(74, 50);
    button7.setTranslateX(10);
    button7.setTranslateY(171);

    button8.setPrefSize(74, 50);
    button8.setTranslateX(86);
    button8.setTranslateY(171);

    button9.setPrefSize(74, 50);
    button9.setTranslateX(162);
    button9.setTranslateY(171);

    add.setPrefSize(74, 50);
    add.setTranslateX(238);
    add.setTranslateY(171);

    clear.setPrefSize(74, 50);
    clear.setTranslateX(314);
    clear.setTranslateY(171);

    button4.setPrefSize(74, 50);
    button4.setTranslateX(10);
    button4.setTranslateY(224);

    button5.setPrefSize(74, 50);
    button5.setTranslateX(86);
    button5.setTranslateY(224);

    button6.setPrefSize(74, 50);
    button6.setTranslateX(162);
    button6.setTranslateY(224);

    subtract.setPrefSize(74, 50);
    subtract.setTranslateX(238);
    subtract.setTranslateY(224);

    backspace.setPrefSize(74, 50);
    backspace.setTranslateX(314);
    backspace.setTranslateY(224);

    button1.setPrefSize(74, 50);
    button1.setTranslateX(10);
    button1.setTranslateY(277);

    button2.setPrefSize(74, 50);
    button2.setTranslateX(86);
    button2.setTranslateY(277);

    button3.setPrefSize(74, 50);
    button3.setTranslateX(162);
    button3.setTranslateY(277);

    multiply.setPrefSize(74, 50);
    multiply.setTranslateX(238);
    multiply.setTranslateY(277);

    power.setPrefSize(74, 50);
    power.setTranslateX(314);
    power.setTranslateY(277);

    equals.setPrefSize(74, 50);
    equals.setTranslateX(314);
    equals.setTranslateY(330);

    button0.setPrefSize(74, 50);
    button0.setTranslateX(86);
    button0.setTranslateY(330);

    leftBracket.setPrefSize(74, 50);
    leftBracket.setTranslateX(10);
    leftBracket.setTranslateY(330);

    rightBracket.setPrefSize(74, 50);
    rightBracket.setTranslateX(162);
    rightBracket.setTranslateY(330);

    divide.setPrefSize(74, 50);
    divide.setTranslateX(238);
    divide.setTranslateY(330);

    textField.setAlignment(Pos.CENTER_LEFT);

    //Giving buttons an ID to use in CSS
    leftBracket.setId("leftBracket");
    rightBracket.setId("rightBracket");
    button0.setId("b0");
    button1.setId("b1");
    button2.setId("b2");
    button3.setId("b3");
    button4.setId("b4");
    button5.setId("b5");
    button6.setId("b6");
    button7.setId("b7");
    button8.setId("b8");
    button9.setId("b9");
    add.setId("add");
    subtract.setId("subtract");
    multiply.setId("multiply");
    divide.setId("divide");
    equals.setId("equals");
    power.setId("power");
    clear.setId("clear");
    backspace.setId("backspace");
    textField.setId("textField");
    root.setId("root");
    exit.setId("exit");

    //Setting focus for all button to false and making the text field uneditable
    exit.setFocusTraversable(false);
    leftBracket.setFocusTraversable(false);
    rightBracket.setFocusTraversable(false);
    textField.setFocusTraversable(true);
    textField.setEditable(false);
    button0.setFocusTraversable(false);
    button1.setFocusTraversable(false);
    button2.setFocusTraversable(false);
    button3.setFocusTraversable(false);
    button4.setFocusTraversable(false);
    button5.setFocusTraversable(false);
    button6.setFocusTraversable(false);
    button7.setFocusTraversable(false);
    button8.setFocusTraversable(false);
    button9.setFocusTraversable(false);
    equals.setFocusTraversable(false);
    clear.setFocusTraversable(false);
    backspace.setFocusTraversable(false);
    power.setFocusTraversable(false);
    add.setFocusTraversable(false);
    subtract.setFocusTraversable(false);
    multiply.setFocusTraversable(false);

    //Handling Events
    EventHandler<ActionEvent> eventHandler = this::actionPerformed;
    button0.addEventHandler(ActionEvent.ACTION, eventHandler);
    button1.addEventHandler(ActionEvent.ACTION, eventHandler);
    button2.addEventHandler(ActionEvent.ACTION, eventHandler);
    button3.addEventHandler(ActionEvent.ACTION, eventHandler);
    button4.addEventHandler(ActionEvent.ACTION, eventHandler);
    button5.addEventHandler(ActionEvent.ACTION, eventHandler);
    button6.addEventHandler(ActionEvent.ACTION, eventHandler);
    button7.addEventHandler(ActionEvent.ACTION, eventHandler);
    button8.addEventHandler(ActionEvent.ACTION, eventHandler);
    button9.addEventHandler(ActionEvent.ACTION, eventHandler);
    power.addEventHandler(ActionEvent.ACTION, eventHandler);
    multiply.addEventHandler(ActionEvent.ACTION, eventHandler);
    divide.addEventHandler(ActionEvent.ACTION, eventHandler);
    add.addEventHandler(ActionEvent.ACTION, eventHandler);
    subtract.addEventHandler(ActionEvent.ACTION, eventHandler);
    equals.addEventHandler(ActionEvent.ACTION, eventHandler);
    clear.addEventHandler(ActionEvent.ACTION, eventHandler);
    backspace.addEventHandler(ActionEvent.ACTION, eventHandler);
    leftBracket.addEventHandler(ActionEvent.ACTION, eventHandler);
    rightBracket.addEventHandler(ActionEvent.ACTION, eventHandler);

    //Adding each button to the root as child of it
    root.getChildren().add(rightBracket);
    root.getChildren().add(leftBracket);
    root.getChildren().add(button0);
    root.getChildren().add(button1);
    root.getChildren().add(button2);
    root.getChildren().add(button3);
    root.getChildren().add(button4);
    root.getChildren().add(button5);
    root.getChildren().add(button6);
    root.getChildren().add(button7);
    root.getChildren().add(button8);
    root.getChildren().add(button9);
    root.getChildren().add(equals);
    root.getChildren().add(add);
    root.getChildren().add(multiply);
    root.getChildren().add(subtract);
    root.getChildren().add(divide);
    root.getChildren().add(power);
    root.getChildren().add(clear);
    root.getChildren().add(backspace);
    root.getChildren().add(textField);
    root.getChildren().add(exit);

    //Making the window draggable
    root.setOnMousePressed(event -> {
      xOffset = event.getSceneX();
      yOffset = event.getSceneY();
    });

    root.setOnMouseDragged(event -> {
      stage.setX(event.getScreenX() - xOffset);
      stage.setY(event.getScreenY() - yOffset);
    });

    //Size of the window 398, 390
    Scene scene = new Scene(root, 398, 390);

    //Load the CSS file
    scene.getStylesheets().add("/sample/css/style.css/");

    stage.setTitle("Calculator");

    //Making the window transparent and opec
    stage.initStyle(StageStyle.TRANSPARENT);
    scene.setFill(null);

    //Setting the main scene
    stage.setScene(scene);
    stage.show();

    //Make the window unable to resize
    stage.setResizable(false);

    //Handling events when user presses keyboard
    scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent keyEvent) -> {
    if(keyEvent.getCode() != null) {
      switch(keyEvent.getCode()) {
        case NUMPAD0:
          button0_clicked();
          break;

        case NUMPAD1:
          button1_clicked();
          break;

        case NUMPAD2:
          button2_clicked();
          break;

        case NUMPAD3:
          button3_clicked();
          break;

        case NUMPAD4:
          button4_clicked();
          break;

        case NUMPAD5:
          button5_clicked();
          break;

        case NUMPAD6:
          button6_clicked();
          break;

        case NUMPAD7:
          button7_clicked();
          break;

        case NUMPAD8:
          button8_clicked();
          break;

        case NUMPAD9:
          button9_clicked();
          break;

        case DIGIT0:
          rightBracket_clicked();
          break;

        case DIGIT9:
          leftBracket_clicked();
          break;

        case DIGIT6:
          power_clicked();
          break;

        case ADD:
          add_clicked();
          break;

        case SUBTRACT:
          subtract_clicked();
          break;

        case MULTIPLY:
          multiply_clicked();
          break;

        case DIVIDE:
          divide_clicked();
          break;

        case ENTER:
          equals_clicked();
          break;

        case BACK_SPACE:
          backspace_clicked();
          break;

        case DELETE:
          clear_clicked();
          break;
      }
    }
    });
  }

  //Handling the action when a button is clicked
  private void actionPerformed(ActionEvent actionEvent) {
    if(actionEvent.getSource() == button0)
      button0_clicked();

    else if(actionEvent.getSource() == button1)
      button1_clicked();

    else if(actionEvent.getSource() == button2)
      button2_clicked();

    else if(actionEvent.getSource() == button3)
      button3_clicked();

    else if(actionEvent.getSource() == button4)
      button4_clicked();

    else if(actionEvent.getSource() == button5)
      button5_clicked();

    else if(actionEvent.getSource() == button6)
      button6_clicked();

    else if(actionEvent.getSource() == button7)
      button7_clicked();

    else if(actionEvent.getSource() == button8)
      button8_clicked();

    else if(actionEvent.getSource() == button9)
      button9_clicked();

    else if(actionEvent.getSource() == power)
      power_clicked();

    else if(actionEvent.getSource() == add)
      add_clicked();

    else if(actionEvent.getSource() == subtract)
      subtract_clicked();

    else if(actionEvent.getSource() == multiply)
      multiply_clicked();

    else if(actionEvent.getSource() == divide)
      divide_clicked();

    else if(actionEvent.getSource() == equals)
      equals_clicked();

    else if(actionEvent.getSource() == backspace)
      backspace_clicked();

    else if(actionEvent.getSource() == clear)
      clear_clicked();

    else if (actionEvent.getSource() == rightBracket)
      rightBracket_clicked();

    else if (actionEvent.getSource() == leftBracket)
      leftBracket_clicked();
  }

  public static void main(String[] args) {
        launch(args);
  }
}