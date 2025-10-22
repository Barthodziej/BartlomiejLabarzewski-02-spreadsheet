## Operations
- `OperationCommand` is responsible for handling the evaluation of available operations.
  - The interface supports operations with multiple `int` arguments
  - The return type has to be `int`
  - It has to be supplied with arguments before execution via `void setArgument(int index, int value)`
  - If the arguments are supplied, the operation can be executed via `int execute()`

    
- `AddCommand` implements `OperationCommand`
  - It takes two arguments `int x`, `int y`
  - The result is `x + y`
  

- `SubCommand` implements `OperationCommand`
  - It takes two arguments `int x`, `int y`
  - The result is `x - y`
  

- `MulCommand` implements `OperationCommand`
  - It takes two arguments `int x`, `int y`
  - The result is `x * y`
  

- `DivCommand` implements `OperationCommand`
  - It takes two arguments `int x`, `int y`
  - The result is `x / y`
  

- `ModCommand` implements `OperationCommand`
  - It takes two arguments `int x`, `int y`
  - The result is `x % y`


- `OperationCommandCreator` is responsible for creating operation commands.
    - It takes a `string` identifier of an operation
    - The result is an empty subtype of `OperationCommand` suitable to the input

## Expressions

- `IExpression` is responsible for representing and evaluating expressions.
  - The expression's return type has to be `int`.
  - Dependencies of the expression have to be provided on construction.
  - `bool canBeEvaluated()` says if all the requirements of an evaluation are met.
  - `bool isEvaluated()` says if the expression is currently evaluated and is able to provide the result.
  - `void evaluate()` evaluates the expression
  - `int getValue()` returns the current value of the expression


- `IValueExpression` extends `IExpression`
  - It represents a fixed value


- `ValueExpression` implements `IValueExpression`
  - It stores the value in its field `value` which has to be provided on construction
  - `bool canBeEvaluated()` returns true (can always be evaluated)
  - `bool isEvaluated()` returns true (is always evaluated)
  - `void evaluate()` doesn't do anything
  - `int getValue()` returns the aggregated `value`


- `IReferenceExpression` extends `IExpression`
  - It represents a reference to the expression in another cell of the same matrix
  - The referred expression has to lie in the same matrix


- `ReferenceExpression` implements `IReferenceExpression`
  - It stores an address of the cell containing referred `IExpression`.
  - It also knows in which matrix it is.
  - It has a field `bool isEvaluated` that's set true on evaluation.
  - `bool canBeEvaluated()` returns true iff the referred cell's expression is already evaluated
  - `bool isEvaluated()` returns true after the successful execution of `void evaluate()`
  - `void evaluate()` can be used iff `canBeEvaluated()`. It copies the value of the referred cell's expression and saves it.
  - `int getValue()` returns the evaluation result.


- `IBinaryOperationExpression` extends `IExpression`
  - It represents an expression that takes two `IExpression` arguments and returns the result of applying a binary operation on their values


- `BinaryOperationExpression` implements `IBinaryOperationExpression`
  - It stores its two arguments in its fields `IExpression left` and `IExpression right`. These have to be set up on construction.
  - It stores the binary operation as an `OperationCommand`. It also has to be set on construction.
  - It has a field `bool isEvaluated` that's set true on evaluation.
  - `bool canBeEvaluated()` returns true iff both arguments can be evaluated.
  - `bool isEvaluated()` returns true iff the expression has been evaluated.
  - `void evaluate()` orders the arguments to evaluate themselves, then it evaluates the result by using their values and the stored `OperationCommand`
  - `int getValue()` returns the evaluation result.


- `IExpressionParser` is responsible for parsing the expression.
  - Its `parse()` method gets a `String` that has to be parsed. The `String` represents an `IExpression`. The result is one of the implementations of `IExpression`
  - Its `injectMatrix()` method sets the matrix on which it works 
  - Its `dependencies()` method returns addresses of all the cells that the given `IExpression` refers to


- `SimpleExpressionParser` implements `IExpressionParser`
  - It processes expressions representations in one of three forms
    - Value in form `<val>`
    - Reference in form `$<address>`
    - Binary operations in form `=<OPERRATION>(<arg1>, <arg2>)` where `<OPERATION>` is a representation of an `OperationCommand` and `<arg1>`, `<arg2>` are representations of either a value or a reference.
  - It has to know in which matrix it operates, as it may return a `ReferenceExpression`, which needs to know its matrix.
  - Its `parse()` method parses the expression to its `IExpression` representation
  - Its `dependencies()` method returns all the cells that the given expression refers to.


### Cells

- `ICell` extends `ICellUpdateListener` and is responsible for:
  - Storing an `IExpression`
  - Giving access to the expression
  - Notifying `ICells` interested in our updates after evaluating its expression


- `Cell` implements `ICell`
  - It may store a subclass of `IExpression` that belongs to it
  - It stores a `CellUpdateManager` for notifying about value of the contained `IExpression`
  - The `CellUpdateManager` is created on construction and is final.
  - It can both be empty or contain an `IExpression`,
  - It provides access to its expression.
  - It has an `update()` method that reevaluates an expression contained by the `Cell` and notifies subscribers about reevaluation (currently, `Cells` referring to our expression).

### Matrices

- `ICellMatrix` is responsible for:
  - Containing the limited plane collection of `Cell`
  - Giving access to a `Cell` on a given address (if that address is in range)
  - Converting the contained plane to a two-dimensional `String` array


- `ArrayCellMatrix` implements `ICellMatrix`
  - `Cells` are contained in a two-dimensional array
  - The constructor takes a two-dimensional array of `Strings` and converts it into a functional matrix
    - Firstly, it fills the stored `Cell` array with new objects. 
    - For each `Cell a` it parses its expression (if it exists) and injects it. When the expression (directly or indirectly) refers another `Cell b`, the `Cell a` is added to the subscribers list of `Cell b` 
    - Now all expressions are set up, put in their corresponding `Cells` and are linked, but they aren't evaluated. We only need to evaluate all expressions. We just need to `update()` each cell.
    - Everything is evaluated


- `ICellAddressParser` is responsible for parsing addresses
  - It has a `parse()` method, that converts a symbolic address to a pair `{row, col}`


- `ExcelCellAddressParser` implements `ICelLAddressParser`
  - The `parse()` method converts an Excel-like `Cell` address in `String` format to a pair `{row, column}`.


- `Spreadsheet` works as a facade for the exercise. 

### Considerations:
- Now, `ArrayCellMatrix` invokes `update()` on each `ICell`. The class is **not** supposed to be dependent on the expression type range, so it may be inefficient to check if each `Cell`'s expression can be evaluated.
  - For example, the `IBinaryOperationExpression` allows for nested binary operation. The size of such expression is unbound. The evaluation possibility check would demand recursive check in every subexpression.
    
  How can we omit this process and just broadcast the update message from already evaluated cells (this is, the ones with IValueExpression)?


- Currently, `ArrayCellMatrix` has a lot of responsibilities. How could we prevent this phenomenon?


- The `ReferenceExpression` demands a way to access the referred expression. That's why it has a reference to the `ICellMatrix` where it lies. However, let's consider this problem:
  - We would want a `ReferenceExpression` to refer to a cell that is **not** present in the same cellMatrix as the follower.
  
  Currently, there is no way to do this, as we construct all the expressions in `CellMatrix` constructor. The `CellMatrix` doesn't even know about other matrices. How could we make it possible for such reference to be implemented?

- `CellUpdateManager` doesn't have an abstraction. Should I add some?

- There is no way to remove expressions from cells. Should I add such option? It would need to update subscribers lists and values.