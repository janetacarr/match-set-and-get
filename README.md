# match-set-and-get
convenience macros for messing with Clojure type definitions.

## Usage
Currently there's only one macro here called `with-setters`.
`with-setters` will create a protocol and reify it per mutable field
in the type definition.

``` clojure
(with-setters
  (deftype Thinger [^:unsynchronized-mutable thing immutable-thing]))

(def t (->Thinger "hi" "there"))

(set-thing t "bye")
```
