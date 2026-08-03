## HashMap Todo

- [ ] HashMap
    - [ ] Create src/main/kotlin/program/expression/value/HashMapValue.kt
        - Wraps a `HashMap<Value<*>, Value<*>>`
    - [ ] Override the `get` and `set` functions in `HashMapValue`
        - These are called when writing `someHashMapValue[someItem]` and `someHashMapValue[someItem] = someValue` in the language
    - [ ] Override the `getFunction` function in `HashMapValue`
        - Add functions that will can be accessed from hash map values, such as remove, keys, values, items, etc.
        - Add accessors that point to functions you create within the class
        - Look at `getFunction` in `ListValue` for similar examples
        - You can also point to the get and set functions to allow them to be called by name as well
    - [ ] Create `hashMap` function in src/main/kotlin/program/expression/BuiltinFunctions.kt
        - Gets one argument: a `ListValue<*>` (of `PairValue`s, but it's not typed, so we will have to check its type below)
        - Loop through the ListValue's internal list and construct a `HashMapValue` from the pairs
            - For each value in the ListValue, cast it to a pair with `.cast<PairValue>()`
        - Returns the HashMapValue
    - [ ] Make a new test in src/test/kotlin/test
    - [ ] Run the test in src/test/kotlin/Tests.kt `private fun tests()`
