# Calculator

## UML Diagram of Calculator App
![UML Diagram](https://i.imgur.com/eej4nem.png)

## 👀 Class Overview 

| Class                   | Description                                                        |
|-------------------------|--------------------------------------------------------------------|
| Token                   | Holds one element in the equation (brackets, number, operation)    |
| TokenType               | Helps specify which type of element the token is                   |
| TokenList               | Holds the tokens added from the calculator                         |
| CalculatorViewModel     | Holds the Calculator Instance in our activity                      |
| Operation               | Describes a math operation (priority, input count, implementation) |
| OperationImplementation | Interface used to declare an operation function                    |
| Calculator              | Calculates a TokenList                                             |
| MainActivity            | Entry point of the application                                     |

## 🧮 The calculation

The calculator ultilizes the [shunting yard algorithm](https://en.wikipedia.org/wiki/Shunting-yard_algorithm) to convert a list of Tokens using infix notation into a postfix form in which well call it RPN form. Then, it ultilizes a secondary value stack to pop and push the numbers from RPN and pass them to the matching function that are bounded to their operation symbols. Some of its advantages is that in RPN form, there are no brackets eliminating one more step of complexity in a calculation process. On the other hand, other algorithms such as [split-and-merge](https://www.thinkmind.org/articles/icsea_2016_12_20_10065.pdf) could be more suitable and even more flexible than the current.

## 🐛 Known Issues

- Operations such as `!5` and `5!` or `5%` and `%5` are computed as the same thing
- Factorial of large numbers are slightly incorrect due to the way the gamma function for float is implemented
- Program UI may appear incorrectly or not work at all on devices with a lower DPI
- In extremelly rare cases, the order of operations may not work as intended
- Build files may require extra manual tweaking
