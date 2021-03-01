using LexerPLSharpVersion.token;

namespace LexerPLSharpVersion.constants {
   public static class ComparisonOperatorsConstants {
      public const char Equality = '=';
      public const char Greater = '>';
      public const char Less = '<';
      public const char ExclamationMark = '!';
      public const char Notequal = '#';
      public const char QuestionMark = '?';

      public static bool IsComparisonOperator(char character) {
         switch (character){
            case Equality:
            case Greater:
            case Less:
            case Notequal:
            case ExclamationMark:
            case QuestionMark:
               return true;
            default:
               return false;
         }
      }
      
      public static TokenType DetectComparisonOperatorType(char character) {
         switch (character){
            case Equality:
               return TokenType.Equal;
            case Greater:
               return TokenType.Greater;
            case Less:
               return TokenType.Less;
            case Notequal:
               return TokenType.Notequal;
            case ExclamationMark:
               return TokenType.ExclamationMark;
            case QuestionMark:
               return TokenType.QuestionMark;
            default:
               return TokenType.Unknown;
         }
      }
   }
}