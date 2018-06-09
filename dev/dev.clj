(ns dev
  "Tools for interactive development with the REPL. This file should
  not be included in a production build of the application.

  Call `(reset)` to reload modified code and (re)start the system.

  The system under development is `system`, referred from
  `com.stuartsierra.component.repl/system`.

  See also https://github.com/stuartsierra/component.repl"
  (:require clojure.tools.namespace.repl
            [com.rpl.specter :refer :all]
            [com.stuartsierra.component :as component]
            [com.stuartsierra.component.repl :refer [set-init system]]
            interface))

;; Do not try to load source code from 'resources' directory
(clojure.tools.namespace.repl/set-refresh-dirs "dev" "src" "test")

(defn dev-system
  "Constructs a system map suitable for interactive development."
  []
  (component/system-map
   :interface
   (interface/new-interface)
   ))

(set-init (fn [_] (dev-system)))

(ns-unmap *ns* '>>>) (defmacro >>> [& body] `(-> system :interface :resources-atom ~@body))
(ns-unmap *ns* 'aws) (defmacro aws [& paths] `(select [ ~@paths ] (-> system :interface :resources-atom deref)))

(./inject-in clojure.core
             [dev >>>]
             [dev aws])
