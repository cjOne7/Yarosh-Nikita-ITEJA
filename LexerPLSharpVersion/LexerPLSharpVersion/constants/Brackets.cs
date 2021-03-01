using LexerPLSharpVersion.token;

namespace LexerPLSharpVersion.constants {
   public static class Brackets {
      private const char CloseRoundBracket = ')';
      private const char OpenRoundBracket = '(';
      
      public static bool IsBracket(char character) {
         switch (character){
            case CloseRoundBracket:
            case OpenRoundBracket:
               return true;
            default:
               return false;
         }
      }
      
      public static TokenType DetectBracketType(char character) {
         switch (character){
            case CloseRoundBracket:
               return TokenType.CloseRoundBracket;
            case OpenRoundBracket:
               return TokenType.OpenRoundBracket;
            default:
               return TokenType.Unknown;
         }
      }
   }
}