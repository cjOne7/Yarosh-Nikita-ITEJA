using System;

namespace LexerPLSharpVersion.token {
   public class Token {
      public TokenType TokenType { get; }
      public string TokenString { get; }
      public int RowPosition { get; }
      public int ColumnPosition { get; }

      public Token(TokenType tokenType, string tokenString, int rowPosition, int columnPosition) {
         TokenType = tokenType;
         TokenString = tokenString ?? throw new ArgumentNullException(nameof(tokenString));
         RowPosition = rowPosition;
         ColumnPosition = columnPosition;
      }

      public override string ToString() {
         return "Type: " + TokenType + ", value: '" + TokenString + "', row: " + RowPosition + ", column: " + ColumnPosition;
      }
   }
}