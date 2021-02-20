using System;
using System.Text.RegularExpressions;

namespace MathCompiler {
   class MainProgram {
      private static void Main(string[] args) {
         // var interpreter = new MathInterpreter("3*2/2+2*3*4-5");
         // Console.WriteLine(interpreter.Calculate());

         var str = "33.1*.2231/2";
         var regex = new Regex(@"^\d*.\d+");
         var match = regex.Match(str);
         Console.WriteLine(match.Index);
         Console.WriteLine(match.Length);
         Console.WriteLine(str.Substring(match.Index, match.Length));
      }
   }
}