(ns sudachi-clj.core-test
  (:require [clojure.test :as t]
            [sudachi-clj.core :as sut]))

(t/deftest noun?-test
  (t/testing "Predicate that is a noun or not"
    (t/are [result part] (= result (sut/noun? part))
      false ["あたし" ["代名詞" "*" "*" "*" "*" "*"]]
      false ["は" ["助詞" "係助詞" "*" "*" "*" "*"]]
      true  ["プログラム" ["名詞" "普通名詞" "サ変可能" "*" "*" "*"]]
      false ["の" ["助詞" "格助詞" "*" "*" "*" "*"]]
      true  ["女の子" ["名詞" "普通名詞" "一般" "*" "*" "*"]]
      false ["です" ["助動詞" "*" "*" "*" "助動詞-デス" "終止形-一般"]])))
