using System;
using System.Text;
using System.Collections.Generic;

namespace MathInterpreter {
   public class MathInterpret {
      private readonly Stack<double> _stack = new Stack<double>();
      private readonly string _mathExpression;
      private int _currentPosition;

      public MathInterpret(string mathExpression) {
         _mathExpression = mathExpression;
      }

      public double Calculate() {
         ReadMathExp();
         return _stack.Pop();
      }

      private bool ReadMathExp() {
         ReadTerm();
         char? sign = ReadPlusMinus();
         while (sign != null){
            ReadTerm();
            var v1 = _stack.Pop();
            var v2 = _stack.Pop();
            var res = sign == '+' ? v1 + v2 : v2 - v1;
            _stack.Push(res);
            sign = ReadPlusMinus();
         }

         return true;
      }

      private bool ReadTerm() {
         ReadFactor();
         char? sign = ReadMulDiv();
         while (sign != null){
            ReadFactor();
            var v1 = _stack.Pop();
            var v2 = _stack.Pop();
            var res = sign == '*' ? v1 * v2 : v2 / v1;
            _stack.Push(res);
            sign = ReadMulDiv();
         }

         return true;
      }

      private bool ReadFactor() {
         return ReadNumber();
      }

      private bool ReadNumber() {
         var stringBuilder = new StringBuilder();
         while (_currentPosition < _mathExpression.Length && char.IsDigit(_mathExpression[_currentPosition])){
            stringBuilder.Append(_mathExpression[_currentPosition++]);
         }

         if (stringBuilder.Length == 0){
            return false;
         }

         _stack.Push(Convert.ToDouble(stringBuilder.ToString()));
         return true;
      }

      private char? ReadPlusMinus() {
         if (_currentPosition >= _mathExpression.Length){
            return null;
         }

         if (_mathExpression[_currentPosition] == '+' || _mathExpression[_currentPosition] == '-'){
            return _mathExpression[_currentPosition++];
         }

         return null;
      }

      private char? ReadMulDiv() {
         if (_currentPosition >= _mathExpression.Length){
            return null;
         }

         if (_mathExpression[_currentPosition] == '*' || _mathExpression[_currentPosition] == '/'){
            return _mathExpression[_currentPosition++];
         }

         return null;
      }
   }
}