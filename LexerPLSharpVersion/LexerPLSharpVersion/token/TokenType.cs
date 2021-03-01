namespace LexerPLSharpVersion.token {
   public enum TokenType {
      Program, Procedure, Call,
      Dot, Semicolon, Colon, Comma, 
      WhiteSpace, NewLine, Tabulator, CarriageReturn,
      Begin, End,
      If, Then, Else,
      Do, While,
      Var, Const, Assignment, Identifier,
      Number,
      Plus, Minus, Multiply, Divide,
      Equal, Notequal, Greater, GreaterOrEqual, Less, LessOrEqual, ExclamationMark, QuestionMark,
      CloseRoundBracket, OpenRoundBracket,
      Unknown, EndOfFile
   }
}