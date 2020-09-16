package sample;


import java.util.ArrayList;
import java.util.Scanner;


class Calculator {
  private static int bracketPairIndex = 0;
  private static int negativeBracketPairIndex = 0;


  //Calculate a math expression that may have brackets
  static String calculate(String inputString) {
    inputString = cleanSpacesAroundOperators(inputString);

    //Validation: Only numbers, brackets and simple arithmetic operators are allowed
    if(!validate(inputString)) {
      throw new IllegalArgumentException(" Bad Input");
    }

    //If there is something like this: -(5*2) turn it to: -1*(5*2)
    inputString = handleNegativeBeforeBrackets(inputString);

    //To store pairs of brackets which contain only a negative number like this: (-3)
    int[][] negativeBracketPairs = findNegativeBrackets(inputString);

    //To store pairs of brackets. Array includes opening bracket index and it's closing bracket index of the inputString
    int[][] bracketPairs = findBrackets(inputString, negativeBracketPairs);

    //Calculate each bracket and replace it in the inputString
    StringBuilder inBracketOperation = new StringBuilder(); //To store the math expression inside of a bracket which does not contain any brackets
    for(int i = bracketPairIndex; i >= 0; i--) {
      for(int insideBracketIndex = bracketPairs[i][0] + 1; insideBracketIndex < bracketPairs[i][1]; insideBracketIndex++) {
        inBracketOperation.append(inputString.charAt(insideBracketIndex));
      }

      String insideBracketResult = calculateNoBrackets(inBracketOperation.toString());

      //Clear the StringBuilder inBracketOperation
      inBracketOperation.delete(0, inBracketOperation.length());

      //Remove the calculated bracket and its content from inputString and replace it with the result of it
      inputString = updateInputString(inputString, insideBracketResult, bracketPairs[i][0], bracketPairs[i][1]);

      //Because of change of indexes after updating inputString, we need to update the bracket pairs indexes
      negativeBracketPairs = findNegativeBrackets(inputString);
      bracketPairs = findBrackets(inputString, negativeBracketPairs);
    }

    //After dealing with brackets, the rest is a math expression without brackets
    return calculateNoBrackets(inputString);
  }

  private static String updateInputString(String inputString, String bracketResult, int openBracketIndex, int closeBracketIndex) {
    StringBuilder newInputString = new StringBuilder();

    for(int inputStringIndex = 0; inputStringIndex < inputString.length(); inputStringIndex++) {
      //If number is negative we need to put the number in a pair of brackets
      if(new BigNumber(0).isGreaterThan(new BigNumber(bracketResult))) {
        if(inputStringIndex == openBracketIndex) {
          newInputString.append('(');
          newInputString.append(bracketResult);
          newInputString.append(')');
          continue;
        }

        if(inputStringIndex > openBracketIndex && inputStringIndex <= closeBracketIndex) {
          continue;
        }

        newInputString.append(inputString.charAt(inputStringIndex));
      } else {
        if(inputStringIndex == openBracketIndex) {
          newInputString.append(bracketResult);
          continue;
        }

        if(inputStringIndex > openBracketIndex && inputStringIndex <= closeBracketIndex) {
          continue;
        }

        newInputString.append(inputString.charAt(inputStringIndex));
      }
    }

    return newInputString.toString();
  }

  //Calculate a math expression which doesnt contain brackets
  private static String calculateNoBrackets(String inputString) {
   ArrayList<BigNumber> operands = new ArrayList<>();
   ArrayList<Character> operators = new ArrayList<>();
   ArrayList<BigNumber> reverseOrderOperands = new ArrayList<>();
   ArrayList<Character> reverseOrderOperators = new ArrayList<>();
   ArrayList<Integer> notOperatorNegatives = new ArrayList<>(); //To store - signs index which are signs not operators

   StringBuilder operand = new StringBuilder();

   //Store operands in an ArrayList
   for(int inputStringIndex = 0; inputStringIndex < inputString.length(); inputStringIndex++) {
     if(inputString.charAt(inputStringIndex) >= '0' && inputString.charAt(inputStringIndex) <= '9') {
       operand.append(inputString.charAt(inputStringIndex));
     } else if(inputStringIndex == 0 && inputString.charAt(inputStringIndex) == '-') {
       operand.append('-');
     } else if(inputString.charAt(inputStringIndex) == '(') {
       continue;
     } else if(inputString.charAt(inputStringIndex) == '-' && inputString.charAt(inputStringIndex - 1) == '(') {
       operand.append('-');
       notOperatorNegatives.add(inputStringIndex);
     } else if(inputString.charAt(inputStringIndex) == ')') {
       continue;
     } else {
       operands.add(new BigNumber(operand.toString()));
       operand.delete(0, operand.length()); //Clean operand StringBuilder
     }
   }

   operands.add(new BigNumber(operand.toString()));

   boolean flag; //true means the - character is a sign not an operator
   //Store operators in an ArrayList
   for(int inputStringIndex = 0; inputStringIndex < inputString.length(); inputStringIndex++) {
     if(inputStringIndex == 0 && inputString.charAt(inputStringIndex) == '-') {
       continue;
     }

     flag = false;
     for(Integer notOperatorNegative : notOperatorNegatives) {
       if(inputStringIndex == notOperatorNegative) {
         flag = true;
         break;
       }
     }

     if(flag) {
       continue;
     }

     if(inputString.charAt(inputStringIndex) == '+' || inputString.charAt(inputStringIndex) == '-' || inputString.charAt(inputStringIndex) == '*' || inputString.charAt(inputStringIndex) == '/' || inputString.charAt(inputStringIndex) == '^') {
       operators.add(inputString.charAt(inputStringIndex));
     }
   }

   //Reverse the order of operands
   for(int i = operands.size() - 1; i >= 0; i--) {
     reverseOrderOperands.add(operands.get(i));
   }

   //Reverse the order of operators
   for(int i = operators.size() - 1; i >= 0; i--) {
     reverseOrderOperators.add(operators.get(i));
   }

   //Items that will be removed from the operands lists after power operation
   ArrayList<BigNumber> itemsToRemove = new ArrayList<>();

   //Take care of all power operations
   for(int i = reverseOrderOperators.size() - 1; i >= 0; i--) {
     if(reverseOrderOperators.get(i) == '^') {
       BigNumber powerResult = operate(reverseOrderOperands.get(i + 1), reverseOrderOperands.get(i), reverseOrderOperators.get(i));
       reverseOrderOperands.set(i + 1, powerResult);

       //Add the redundant operand to remove list
       itemsToRemove.add(reverseOrderOperands.get(i));
     }
   }

   //Remove all redundant operands
   for(BigNumber index : itemsToRemove) {
     for(int j = reverseOrderOperands.size() - 1; j >= 0; j--) {
       if(reverseOrderOperands.get(j) == index) {
         reverseOrderOperands.remove(j);
       }
     }
   }

   //Remove all ^ operators from list because they are all done
   reverseOrderOperators.removeIf(o -> o == '^');

   //Take care of all multiplication and division operations
   for(int i = reverseOrderOperators.size() - 1; i >= 0 ; i--) {
     if(reverseOrderOperators.get(i) == '*' || reverseOrderOperators.get(i) == '/') {
       if(reverseOrderOperators.get(i) == '*') {
         BigNumber multiplicationResult = operate(reverseOrderOperands.get(i + 1), reverseOrderOperands.get(i), reverseOrderOperators.get(i));
         reverseOrderOperands.set(i + 1, multiplicationResult);
         reverseOrderOperands.remove(i);
         reverseOrderOperators.remove(i);
       } else {
         BigNumber divisionResult = operate(reverseOrderOperands.get(i + 1), reverseOrderOperands.get(i), reverseOrderOperators.get(i));
         reverseOrderOperands.set(i + 1, divisionResult);
         reverseOrderOperands.remove(i);
         reverseOrderOperators.remove(i);
       }
     }
   }

   //Take care of all addition and subtraction operations
   for(int i = reverseOrderOperators.size() - 1; i >= 0 ; i--) {
     if(reverseOrderOperators.get(i) == '+' || reverseOrderOperators.get(i) == '-') {
       if(reverseOrderOperators.get(i) == '+') {
         BigNumber additionResult = operate(reverseOrderOperands.get(i + 1), reverseOrderOperands.get(i), reverseOrderOperators.get(i));
         reverseOrderOperands.set(i + 1, additionResult);
         reverseOrderOperands.remove(i);
         reverseOrderOperators.remove(i);
       } else {
         BigNumber subtractionResult = operate(reverseOrderOperands.get(i + 1), reverseOrderOperands.get(i), reverseOrderOperators.get(i));
         reverseOrderOperands.set(i + 1, subtractionResult);
         reverseOrderOperands.remove(i);
         reverseOrderOperators.remove(i);
       }
     }
   }

   return reverseOrderOperands.get(0).toString();
  }

  private static BigNumber operate(BigNumber firstOperand, BigNumber secondOperand, char operator) {
   switch(operator) {
     case '^':
       return firstOperand.pow(secondOperand);
     case '*':
       return firstOperand.multiply(secondOperand);
     case '/':
       return firstOperand.divide(secondOperand);
     case '+':
       return firstOperand.add(secondOperand);
     case '-':
       return firstOperand.subtract(secondOperand);
   }

   return new BigNumber(0);
  }

  //Find opening brackets index and it's closing bracket index and store them in an array as pairs
  private static int[][] findBrackets(String inputString, int[][] negativeBracketPairs) {
   int[][] bracketPairs = new int[inputString.length()][2];

   bracketPairIndex = 0;

   boolean flag; //true means the bracket contains a math expression and does not contain a simple negative number
   for(int inputStringIndex = 0; inputStringIndex < inputString.length(); inputStringIndex++) {
     if(inputString.charAt(inputStringIndex) == '(') {
       flag = true;
       for(int i = 0; i <= negativeBracketPairIndex; i++) {
         if(inputStringIndex == negativeBracketPairs[i][0]) {
           flag = false;
           break;
         }
       }

       boolean flag2 = true; //true means an open bracket is closed, false means it's still open
       if(flag) {
         bracketPairs[bracketPairIndex][0] = inputStringIndex;

         for(int i = inputStringIndex + 1;; i++) {
           if(inputString.charAt(i) == '(') {
             flag2 = false;
           }

           if(inputString.charAt(i) == ')' && flag2) {
             bracketPairs[bracketPairIndex++][1] = i;
             break;
           }

           if(inputString.charAt(i) == ')') {
             flag2 = true;
           }
         }
       }
     }
   }

   bracketPairIndex--;

   return bracketPairs;
  }

  private static int[][] findNegativeBrackets(String inputString) {
   int[][] negativeBracketPairs = new int[inputString.length()][2];

   negativeBracketPairIndex = 0;

   boolean flag; //true means its a negative bracket pair. false means its a bracket pair containing a math expression
   for(int inputStingIndex = 0; inputStingIndex < inputString.length(); inputStingIndex++) {
     if(inputString.charAt(inputStingIndex) == '(') {
       flag = true;
       if(inputString.charAt(inputStingIndex + 1) == '-') {
         for(int i = inputStingIndex + 2; inputString.charAt(i) != ')'; i++) {
           if(!(inputString.charAt(i) >= '0' && inputString.charAt(i) <= '9')) {
             flag = false;
             break;
           }
         }
       } else { flag = false; }

       if(flag) {
         negativeBracketPairs[negativeBracketPairIndex][0] = inputStingIndex;

         for(int i = inputStingIndex + 2; i < inputString.length(); i++) {
           if(inputString.charAt(i) == ')') {
             negativeBracketPairs[negativeBracketPairIndex++][1] = i;
             break;
           }
         }
       }
     }
   }

   if(negativeBracketPairIndex != 0) {
     negativeBracketPairIndex--;
   }

   //Making unused array elements -1 so later in other methods there wont be failure in if conditions
   if(negativeBracketPairs[negativeBracketPairIndex][1] == 0) {
     for(int i = 0; i < inputString.length(); i++) {
       negativeBracketPairs[i][0] = -1;
       negativeBracketPairs[i][1] = -1;
     }
   } else {
     for(int i = negativeBracketPairIndex + 1; i < inputString.length(); i++) {
       negativeBracketPairs[i][0] = -1;
       negativeBracketPairs[i][1] = -1;
     }
   }

  return negativeBracketPairs;
  }

  private static String handleNegativeBeforeBrackets(String inputString) {
    StringBuilder newInputString = new StringBuilder();

    for(int inputStringIndex = 0; inputStringIndex < inputString.length(); inputStringIndex++) {
      if(inputString.charAt(inputStringIndex) == '-' && inputString.charAt(inputStringIndex + 1) == '(') {
        newInputString.append('-');
        newInputString.append('1');
        newInputString.append('*');
      } else {
        newInputString.append(inputString.charAt(inputStringIndex));
      }
    }

    return newInputString.toString();
  }

  private static boolean validate(String inputString) {
    for(int inputStringIndex = 0; inputStringIndex < inputString.length(); inputStringIndex++) {
      if(!((inputString.charAt(inputStringIndex) >= '0' && inputString.charAt(inputStringIndex) <= '9') || inputString.charAt(inputStringIndex) == '(' || inputString.charAt(inputStringIndex) == ')' || inputString.charAt(inputStringIndex) == '+' || inputString.charAt(inputStringIndex) == '^' || inputString.charAt(inputStringIndex) == '/' || inputString.charAt(inputStringIndex) == '*' || inputString.charAt(inputStringIndex) == '-')) {
        return false;
      }
    }

    return true;
  }

  private static String cleanSpacesAroundOperators(String inputString) {
    StringBuilder cleanInputString = new StringBuilder();

    for(int inputStringIndex = 0; inputStringIndex < inputString.length(); inputStringIndex++) {
      if(inputString.charAt(inputStringIndex) == ' ') {
        continue;
      }

      cleanInputString.append(inputString.charAt(inputStringIndex));
    }

    return cleanInputString.toString();
  }


  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    System.out.println("--------------------------------------------");
    System.out.println("Available Operators: +  -  *  /  ^");
    System.out.println("Example: 5+2^2*(5+3)");
    System.out.println("--------------------------------------------");

    while(true) {
      System.out.println("Enter the operation: (For Exiting Type: exit)");

      String inputString = input.nextLine();

      if(inputString.equals("exit") || inputString.equals("Exit")) {
        System.exit(0);
      }

      System.out.println("Result: " + calculate(inputString));
      System.out.println("--------------------------------------------");
    }
  }
}