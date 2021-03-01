using System;
using LexerPLSharpVersion.token;

namespace LexerPLSharpVersion.constants {
   public static class KeyWords {
      private const string Program = "program";
      private const string Procedure = "procedure";
      private const string Call = "call";
      private const string Begin = "begin";
      private const string End = "end";
      private const string If = "if";
      private const string Then = "then";
      private const string Else = "else";
      private const string Do = "do";
      private const string While = "while";
      private const string Var = "var";
      private const string Const = "const";

      public static TokenType DetectKeyWordType(string value) {
         switch (value.ToLower()){
            case Program:
               return TokenType.Program;
            case Procedure:
               return TokenType.Procedure;
            case Call:
               return TokenType.Call;
            case Begin:
               return TokenType.Begin;
            case End:
               return TokenType.End;
            case If:
               return TokenType.If;
            case Then:
               return TokenType.Then;
            case Else:
               return TokenType.Else;
            case While:
               return TokenType.While;
            case Do:
               return TokenType.Do;
            case Var:
               return TokenType.Var;
            case Const:
               return TokenType.Const;
            default:
               return TokenType.Identifier;
         }
      }
   }
}