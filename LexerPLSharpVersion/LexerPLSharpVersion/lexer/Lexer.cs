using System;
using System.Collections.Generic;
using System.Text;
using LexerPLSharpVersion.constants;
using LexerPLSharpVersion.token;

namespace LexerPLSharpVersion.lexer {
   public class Lexer {
      private readonly string[] _keyWords = {
         KeyWordConstants.Program, KeyWordConstants.Procedure, KeyWordConstants.Call, KeyWordConstants.Begin
         , KeyWordConstants.End, KeyWordConstants.If, KeyWordConstants.Then, KeyWordConstants.Else
         , KeyWordConstants.While, KeyWordConstants.Do, KeyWordConstants.Var, KeyWordConstants.Const
      };

      private int CurrentPosition { get; set; }
      private int CurrentLine { get; set; } = 1;

      private readonly List<Token> _tokens = new List<Token>();
      private readonly StringBuilder _stringBuffer = new StringBuilder();
      private Token _token;

      public List<Token> GetTokens(string code) {
         while (CurrentPosition < code.Length){
            char character = code[CurrentPosition];
            if (char.IsLetter(character)){
               while (char.IsLetter(character)){
                  _stringBuffer.Append(character);
                  if (++CurrentPosition == code.Length){
                     break;
                  }

                  character = code[CurrentPosition];
               }

               var str = _stringBuffer.ToString();
               _token = IsKeyWord(str)
                  ? new Token(DetectKeyWordType(str), str, CurrentLine, CurrentPosition)
                  : new Token(TokenType.Identifier, str, CurrentLine, CurrentPosition);
               _tokens.Add(_token);
               _stringBuffer.Clear();
            }
            else if (char.IsDigit(character)){
               while (char.IsDigit(character)){
                  _stringBuffer.Append(character);
                  if (++CurrentPosition == code.Length){
                     break;
                  }

                  character = code[CurrentPosition];
               }

               _token = new Token(TokenType.Number, _stringBuffer.ToString(), CurrentLine, CurrentPosition);
               _tokens.Add(_token);
               _stringBuffer.Clear();
            }
            else if (IsMathOperator(character)){
               AddToken(DetectMathOperatorType(character), character.ToString());
            }
            else if (IsWhiteChar(character)){
               AddToken(DetectWhiteCharType(character), EscapeWhiteChars(character));
            }
            else if (IsSeparator(character)){
               if (CurrentPosition < code.Length - 1 && character == SeparatorConstants.Colon &&
                   code[CurrentPosition + 1] == ComparisonOperatorsConstants.Equality){
                  AddToken(TokenType.Assignment, string.Concat(character, code[CurrentPosition + 1]));
                  CurrentPosition++;
               }
               else if (CurrentPosition == code.Length - 1 && code[CurrentPosition] == SeparatorConstants.Dot){
                  AddToken(TokenType.EndOfFile, character.ToString());
               }
               else{
                  AddToken(DetectSeparatorType(character), character.ToString());
               }
            }
            else if (IsComparisonOperator(character)){
               if (CurrentPosition < code.Length - 1 &&
                   code[CurrentPosition + 1] == ComparisonOperatorsConstants.Equality){
                  var type = character == ComparisonOperatorsConstants.Greater
                     ? TokenType.GreaterOrEqual
                     : TokenType.LessOrEqual;
                  AddToken(type, string.Concat(character, code[CurrentPosition + 1]));
                  CurrentPosition++;
                  continue;
               }

               AddToken(DetectComparisonOperatorType(character), character.ToString());
            }
            else{
               AddToken(TokenType.Unknown, character.ToString());
            }
         }

         return _tokens;
      }

      private bool IsKeyWord(string str) {
         return Array.IndexOf(_keyWords, str) != -1;
      }

      private void AddToken(TokenType tokenType, string value) {
         CurrentPosition++;
         _token = new Token(tokenType, value, CurrentLine, CurrentPosition);
         _tokens.Add(_token);
         if (value == WhiteCharsConstants.EscapedNewLine){
            CurrentLine++;
         }
      }

      private static bool IsComparisonOperator(char character) {
         switch (character){
            case ComparisonOperatorsConstants.Equality:
            case ComparisonOperatorsConstants.Greater:
            case ComparisonOperatorsConstants.Less:
            case ComparisonOperatorsConstants.Notequal:
            case ComparisonOperatorsConstants.ExclamationMark:
               return true;
            default:
               return false;
         }
      }

      private static bool IsMathOperator(char character) {
         switch (character){
            case OperatorConstants.Plus:
            case OperatorConstants.Minus:
            case OperatorConstants.Divide:
            case OperatorConstants.Multiply:
               return true;
            default:
               return false;
         }
      }

      private static bool IsSeparator(char character) {
         switch (character){
            case SeparatorConstants.Dot:
            case SeparatorConstants.Comma:
            case SeparatorConstants.Colon:
            case SeparatorConstants.Semicolon:
               return true;
            default:
               return false;
         }
      }

      private static bool IsWhiteChar(char character) {
         switch (character){
            case WhiteCharsConstants.NewLine:
            case WhiteCharsConstants.Tabulator:
            case WhiteCharsConstants.CarriageReturn:
            case WhiteCharsConstants.WhiteSpace:
               return true;
            default:
               return false;
         }
      }

      private static string EscapeWhiteChars(char character) {
         switch (character){
            case WhiteCharsConstants.NewLine:
               return WhiteCharsConstants.EscapedNewLine;
            case WhiteCharsConstants.Tabulator:
               return WhiteCharsConstants.EscapedTabulator;
            case WhiteCharsConstants.CarriageReturn:
               return WhiteCharsConstants.EscapedCarriageReturn;
            case WhiteCharsConstants.WhiteSpace:
               return " ";
         }

         return "";
      }

      private static TokenType DetectWhiteCharType(char character) {
         switch (character){
            case WhiteCharsConstants.WhiteSpace:
               return TokenType.WhiteSpace;
            case WhiteCharsConstants.Tabulator:
               return TokenType.Tabulator;
            case WhiteCharsConstants.CarriageReturn:
               return TokenType.CarriageReturn;
            case WhiteCharsConstants.NewLine:
               return TokenType.NewLine;
            default:
               return TokenType.Unknown;
         }
      }

      private static TokenType DetectComparisonOperatorType(char character) {
         switch (character){
            case ComparisonOperatorsConstants.Equality:
               return TokenType.Equal;
            case ComparisonOperatorsConstants.Greater:
               return TokenType.Greater;
            case ComparisonOperatorsConstants.Less:
               return TokenType.Less;
            case ComparisonOperatorsConstants.Notequal:
               return TokenType.Notequal;
            case ComparisonOperatorsConstants.ExclamationMark:
               return TokenType.ExclamationMark;
            default:
               return TokenType.Unknown;
         }
      }

      private static TokenType DetectMathOperatorType(char character) {
         switch (character){
            case OperatorConstants.Divide:
               return TokenType.Divide;
            case OperatorConstants.Minus:
               return TokenType.Minus;
            case OperatorConstants.Multiply:
               return TokenType.Multiply;
            case OperatorConstants.Plus:
               return TokenType.Plus;
            default:
               return TokenType.Unknown;
         }
      }

      private static TokenType DetectSeparatorType(char character) {
         switch (character){
            case SeparatorConstants.Colon:
               return TokenType.Colon;
            case SeparatorConstants.Comma:
               return TokenType.Comma;
            case SeparatorConstants.Dot:
               return TokenType.Dot;
            case SeparatorConstants.Semicolon:
               return TokenType.Semicolon;
            default:
               return TokenType.Unknown;
         }
      }

      private static TokenType DetectKeyWordType(string value) {
         switch (value){
            case KeyWordConstants.Program:
               return TokenType.Program;
            case KeyWordConstants.Procedure:
               return TokenType.Procedure;
            case KeyWordConstants.Call:
               return TokenType.Call;
            case KeyWordConstants.Begin:
               return TokenType.Begin;
            case KeyWordConstants.End:
               return TokenType.End;
            case KeyWordConstants.If:
               return TokenType.If;
            case KeyWordConstants.Then:
               return TokenType.Then;
            case KeyWordConstants.Else:
               return TokenType.Else;
            case KeyWordConstants.While:
               return TokenType.While;
            case KeyWordConstants.Do:
               return TokenType.Do;
            case KeyWordConstants.Var:
               return TokenType.Var;
            case KeyWordConstants.Const:
               return TokenType.Const;
            default:
               return TokenType.Unknown;
         }
      }
   }
}