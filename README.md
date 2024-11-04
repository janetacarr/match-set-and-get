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

### Motivation
I created `with-setters` because one can only call `set!` at the top-level
of a type's method, not inside a lambda (they don't get closed over). This
does not work:

```clojure
;; From Clojuredocs.org
;; does not work.
(deftype Test [^:unsynchronized-mutable x]
    ITestProtocol
    (act [this] (fn [o] (set! x o))))
```
If you're trying to create a perforamnt game in CLJS, this is tricky since JS land
loves to lean on 'callbacks'.

One way around this limitatiton is to create a setter method for the other methods to
call. As you can imagine, it's annoying to try and create a separate protocol with setters for
each mutable field in a type definition. I reckon this was done for a good reason, so 
use this macro if you understand that you actually need mutable fields across 
your type definition. 

