using LexerPLSharpVersion.token;

namespace LexerPLSharpVersion.constants {
   public static class MathOperators {
      private const char Plus = '+';
      private const char Minus = '-';
      private const char Multiply = '*';
      private const char Divide = '/';
      
      public static bool IsMathOperator(char character) {
         switch (character){
            case Plus:
            case Minus:
            case Divide:
            case Multiply:
               return true;
            default:
               return false;
         }
      }
      
      public static TokenType DetectMathOperatorType(char character) {
         switch (character){
            case Divide:
               return TokenType.Divide;
            case Minus:
               return TokenType.Minus;
            case Multiply:
               return TokenType.Multiply;
            case Plus:
               return TokenType.Plus;
            default:
               return TokenType.Unknown;
         }
      }
   }
}