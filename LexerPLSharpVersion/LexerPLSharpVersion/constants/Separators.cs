using LexerPLSharpVersion.token;

namespace LexerPLSharpVersion.constants {
   public static class Separators {
      public const char Dot = '.';
      public const char Colon = ':';
      private const char Semicolon = ';';
      private const char Comma = ',';
      
      public static bool IsSeparator(char character) {
         switch (character){
            case Dot:
            case Comma:
            case Colon:
            case Semicolon:
               return true;
            default:
               return false;
         }
      }
      
      public static TokenType DetectSeparatorType(char character) {
         switch (character){
            case Comma:
               return TokenType.Comma;
            case Dot:
               return TokenType.Dot;
            case Semicolon:
               return TokenType.Semicolon;
            default:
               return TokenType.Unknown;
         }
      }
   }
}