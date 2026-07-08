## Todo

- separate the parsing and execution logic a bit more
- add empty/null statement for empty statement lists
- fix array access after call: someFunc()[0]
- random function
- add properties to many values to replace some functions (item.tag instead of tag(item), etc)
- rewrite expression parsing
- struct this keyword in functions
- struct access by ["id"]
- struct destructuring?
- struct get function? (key: get { return this.a + 1; })
- struct variable name shorthand (id instead of id: id)
- allow del as an alternative to remove functions
- better error context (source location, etc)
- unify some of the logic between custom and builtin functions (parsing and arguments)
- static types?
- enums?
- list comprehension?
- in-language errors, exceptions?
