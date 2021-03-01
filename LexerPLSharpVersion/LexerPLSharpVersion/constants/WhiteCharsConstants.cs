using LexerPLSharpVersion.token;

namespace LexerPLSharpVersion.constants {
   public static class WhiteCharsConstants {
      public const char WhiteSpace = ' ';
      public const char NewLine = '\n';
      public const char Tabulator = '\t';
      public const char CarriageReturn = '\r';
      public const string EscapedCarriageReturn = "\\r";
      public const string EscapedNewLine = "\\n";
      public const string EscapedTabulator = "\\n";

      public static bool IsWhiteChar(char character) {
         switch (character){
            case NewLine:
            case Tabulator:
            case CarriageReturn:
            case WhiteSpace:
               return true;
            default:
               return false;
         }
      }

      public static TokenType DetectWhiteCharType(char character) {
         switch (character){
            case WhiteSpace:
               return TokenType.WhiteSpace;
            case Tabulator:
               return TokenType.Tabulator;
            case CarriageReturn:
               return TokenType.CarriageReturn;
            case NewLine:
               return TokenType.NewLine;
            default:
               return TokenType.Unknown;
         }
      }
      
      public static string EscapeWhiteChars(char character) {
         switch (character){
            case NewLine:
               return EscapedNewLine;
            case Tabulator:
               return EscapedTabulator;
            case CarriageReturn:
               return EscapedCarriageReturn;
            case WhiteSpace:
               return " ";
         }

         return "";
      }
   }
}