using System;

namespace MathInterpreter {
   class Program {
      private static void Main(string[] args) {
         var mathInterpreter = new MathInterpret("3*2/2+2*3*4-5");
         Console.WriteLine(mathInterpreter.Calculate());
      }
   }
}