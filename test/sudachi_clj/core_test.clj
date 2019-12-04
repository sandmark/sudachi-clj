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
