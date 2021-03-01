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

      private string Code { get; set; }
      private int CurrentPosition { get; set; }
      private int CurrentLine { get; set; } = 1;

      private readonly List<Token> _tokens = new List<Token>();
      private readonly StringBuilder _stringBuffer = new StringBuilder();
      private Token _token;

      public IEnumerable<Token> GetTokens(string code) {
         Code = code;
         while (CurrentPosition < code.Length){
            var character = code[CurrentPosition];
            if (char.IsLetter(character)){
               ReadWord(character);
            }
            else if (char.IsDigit(character)){
               ReadNumber(character);
            }
            else if (IsMathOperator(character)){
               AddToken(DetectMathOperatorType(character), character.ToString());
            }
            else if (WhiteCharsConstants.IsWhiteChar(character)){
               AddToken(WhiteCharsConstants.DetectWhiteCharType(character)
                  , WhiteCharsConstants.EscapeWhiteChars(character));
            }
            else if (IsSeparator(character)){
               if (CurrentPosition < code.Length - 1 && character == SeparatorConstants.Colon &&
                   code[CurrentPosition + 1] == ComparisonOperatorsConstants.Equality){
                  AddToken(TokenType.Assignment, string.Concat(character, code[CurrentPosition + 1]));
                  CurrentPosition++;
               }
               else if (CurrentPosition == code.Length - 1 && character == SeparatorConstants.Dot){
                  AddToken(TokenType.EndOfFile, character.ToString());
               }
               else{
                  AddToken(DetectSeparatorType(character), character.ToString());
               }
            }
            else if (IsBracket(character)){
               AddToken(DetectBracketType(character), character.ToString());
            }
            else if (ComparisonOperatorsConstants.IsComparisonOperator(character)){
               if (CurrentPosition < code.Length - 1 &&
                   code[CurrentPosition + 1] == ComparisonOperatorsConstants.Equality){
                  var type = character == ComparisonOperatorsConstants.Greater
                     ? TokenType.GreaterOrEqual
                     : TokenType.LessOrEqual;
                  AddToken(type, string.Concat(character, code[CurrentPosition + 1]));
                  CurrentPosition++;
               }
               else{
                  AddToken(ComparisonOperatorsConstants.DetectComparisonOperatorType(character), character.ToString());
               }
            }
            else{
               AddToken(TokenType.Unknown, character.ToString());
            }
         }

         return _tokens;
      }

      private void ReadWord(char character) {
         while (char.IsLetter(character)){
            _stringBuffer.Append(character);
            if (++CurrentPosition == Code.Length){
               break;
            }

            character = Code[CurrentPosition];
         }

         var str = _stringBuffer.ToString();
         _token = IsKeyWord(str)
            ? new Token(DetectKeyWordType(str), str, CurrentLine, CurrentPosition)
            : new Token(TokenType.Identifier, str, CurrentLine, CurrentPosition);
         _tokens.Add(_token);
         _stringBuffer.Clear();
      }

      private void ReadNumber(char character) {
         while (char.IsDigit(character)){
            _stringBuffer.Append(character);
            if (++CurrentPosition == Code.Length){
               break;
            }

            character = Code[CurrentPosition];
         }

         _token = new Token(TokenType.Number, _stringBuffer.ToString(), CurrentLine, CurrentPosition);
         _tokens.Add(_token);
         _stringBuffer.Clear();
      }

      private bool IsKeyWord(string value) {
         return Array.IndexOf(_keyWords, value.ToLower()) != -1;
      }

      private void AddToken(TokenType tokenType, string value) {
         CurrentPosition++;
         _token = new Token(tokenType, value, CurrentLine, CurrentPosition);
         _tokens.Add(_token);
         if (value == WhiteCharsConstants.EscapedNewLine){
            CurrentLine++;
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

      private static bool IsBracket(char character) {
         switch (character){
            case BracketConstants.CloseRoundBracket:
            case BracketConstants.OpenRoundBracket:
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

      private static TokenType DetectBracketType(char character) {
         switch (character){
            case BracketConstants.CloseRoundBracket:
               return TokenType.CloseRoundBracket;
            case BracketConstants.OpenRoundBracket:
               return TokenType.OpenRoundBracket;
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
         switch (value.ToLower()){
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