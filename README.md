# match-set-and-get
[![Clojars Project](https://img.shields.io/clojars/v/com.janetacarr/match-set-and-get.svg)](https://clojars.org/com.janetacarr/match-set-and-get)

Convenience macros for messing with Clojure type definitions.

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
