using System.Collections.Generic;
using System.Text;
using LexerPLSharpVersion.constants;
using LexerPLSharpVersion.token;

namespace LexerPLSharpVersion.lexer {
   public class Lexer {
      private string Code { get; set; }
      private int CurrentPosition { get; set; }
      private int CurrentLine { get; set; } = 1;

      private readonly StringBuilder _stringBuffer = new StringBuilder();
      private readonly List<Token> _tokens = new List<Token>();
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
            else if (MathOperators.IsMathOperator(character)){
               AddToken(MathOperators.DetectMathOperatorType(character), character.ToString());
            }
            else if (WhiteChars.IsWhiteChar(character)){
               AddToken(WhiteChars.DetectWhiteCharType(character)
                  , WhiteChars.EscapeWhiteChars(character));
            }
            else if (Separators.IsSeparator(character)){
               if (CurrentPosition < code.Length - 1 && character == Separators.Colon &&
                   code[CurrentPosition + 1] == CompareOperators.Equality){
                  AddToken(TokenType.Assignment, string.Concat(character, code[CurrentPosition + 1]));
                  CurrentPosition++;
               }
               else if (CurrentPosition == code.Length - 1 && character == Separators.Dot){
                  AddToken(TokenType.EndOfFile, character.ToString());
               }
               else{
                  AddToken(Separators.DetectSeparatorType(character), character.ToString());
               }
            }
            else if (Brackets.IsBracket(character)){
               AddToken(Brackets.DetectBracketType(character), character.ToString());
            }
            else if (CompareOperators.IsComparisonOperator(character)){
               if (CurrentPosition < code.Length - 1 &&
                   code[CurrentPosition + 1] == CompareOperators.Equality){
                  var type = character == CompareOperators.Greater
                     ? TokenType.GreaterOrEqual
                     : TokenType.LessOrEqual;
                  AddToken(type, string.Concat(character, code[CurrentPosition + 1]));
                  CurrentPosition++;
               }
               else{
                  AddToken(CompareOperators.DetectComparisonOperatorType(character), character.ToString());
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
         _token = new Token(KeyWords.DetectKeyWordType(str), str, CurrentLine, CurrentPosition);
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

      private void AddToken(TokenType tokenType, string value) {
         CurrentPosition++;
         _token = new Token(tokenType, value, CurrentLine, CurrentPosition);
         _tokens.Add(_token);
         if (value == WhiteChars.EscapedNewLine){
            CurrentLine++;
         }
      }

   }
}