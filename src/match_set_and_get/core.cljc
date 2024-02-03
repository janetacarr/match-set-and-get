(ns match-set-and-get.core)

(defn mutable-field?
  [field]
  (let [metadata (meta field)]
    #?(:cljs (:mutable metadata)
       :clj (or (:unsynchronized-mutable metadata)
                (:volatile-mutable metadata)
                (:atomic-mutable metadata)
                (:mutable metadata)))))

(defmacro with-setters
  "Creates a series of setter protocols for each mutable field in
  `type-definition` and adds their reification to the `type-definition`."
  [type-definition]
  (let [mutable-fields (filter mutable-field? (nth type-definition 2))
        protocol-names (map (fn [sym]
                              (symbol (str "Set" (clojure.string/capitalize sym))))
                            mutable-fields)
        protocol-methods (map (fn [sym]
                                `( ~(symbol (str "set-" sym)) ~(vector 'this 'val)
                                  ~(list 'set! sym 'val)))
                              mutable-fields)
        names+methods (interleave protocol-names protocol-methods)]
    `(do
       ~@(map (fn [sym]
                `(defprotocol ~(symbol (str "Set" (clojure.string/capitalize sym)))
                   ~(str "A protocol to set the mutable field '" sym "' in a type")
                   (~(symbol (str "set-" sym)) ~(vector 'this 'val)) ))
              mutable-fields)
       ~(into names+methods (reverse type-definition)))))
