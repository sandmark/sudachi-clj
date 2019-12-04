(ns sudachi-clj.core-test
  (:require [clojure.test :as t]
            [fudje.sweet :as fs]
            [sudachi-clj.core :as sut]))

(defn- setup-system-fixture [f]
  (try
    (sut/start)
    (f)
    (finally
      (sut/stop))))

(t/use-fixtures :once setup-system-fixture)

(t/deftest analyze-test
  (t/testing "Returns a vector of a vector: [surface part]"
    (t/is (compatible
           (sut/analyze "形態素解析のテストです")
           (fs/contains-in
            [[(fs/checker string?)
              [(fs/checker string?)]]]))))

  (t/testing "Supports mode A, B and C"
    (let [s "選挙管理委員会"]
      (t/are [sentence mode result] (= (->> (sut/analyze sentence :mode mode)
                                            (map first))
                                       result)
        s :a ["選挙" "管理" "委員" "会"]
        s :b ["選挙" "管理" "委員会"]
        s :c ["選挙管理委員会"]))))

(t/deftest noun?-test
  (t/testing "Predicate that is a noun or not"
    (t/are [result part] (= result (sut/noun? part))
      false ["あたし" ["代名詞" "*" "*" "*" "*" "*"]]
      false ["は" ["助詞" "係助詞" "*" "*" "*" "*"]]
      true  ["プログラム" ["名詞" "普通名詞" "サ変可能" "*" "*" "*"]]
      false ["の" ["助詞" "格助詞" "*" "*" "*" "*"]]
      true  ["女の子" ["名詞" "普通名詞" "一般" "*" "*" "*"]]
      false ["です" ["助動詞" "*" "*" "*" "助動詞-デス" "終止形-一般"]])))
