using System;
using System.IO;
using LexerPLSharpVersion.lexer;
using LexerPLSharpVersion.token;

namespace LexerPLSharpVersion {
   internal class Program {
      public static void Main(string[] args) {
         var text = File.ReadAllText("../../Program.txt");
         var lexer = new Lexer();
         var tokens = lexer.GetTokens(text);
         foreach (var token in tokens){
            Console.WriteLine(token);
         }
      }
   }
}