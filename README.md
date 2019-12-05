# sudachi-clj

A Clojure wrapper library for [Sudachi](https://github.com/WorksApplications/Sudachi), a Japanese morphological analyzer written in Java.

## Usage

### Sudachi Dictionary
The morphological analysis engine requires a Sudachi dictionary, which can be downloaded from [here](https://github.com/WorksApplications/SudachiDict).

Once you have downloaded the Sudachi dictionary file, place it in any location (e.g. /path/to/system_core.dic).

By setting the path to the dictionary file in the environment variable `SUDACHI_DICTIONARY_FILE`, the system can automatically locate the dictionary. Or you can also specify the location as an argument of the `start` function described below.

### REPL
Let's play with lein REPL:

```
lein repl
sudachi-clj.core=>
```

In order to perform morphological analysis, you must activate the Sudachi system by calling `sudachi-clj.core/start` function.

```
sudachi-clj.core=> (start)
:started
```

Or you can manually specify the location of the Sudachi dictionary, instead of setting the environment variable `SUDACHI_DICTIONARY_FILE`.

```
sudachi-clj.core=> (start :dictionary-file "/path/to/system_core.dic")
:started
```

Now you are ready to analyze sentences. Let's try as follows:

```
sudachi-clj.core=> (analyze "宇宙、生命、そして万物についての究極の疑問の答え")
[["宇宙" ["名詞" "普通名詞" "一般" "*" "*" "*"]]
 ["、" ["補助記号" "読点" "*" "*" "*" "*"]]
 ["生命" ["名詞" "普通名 詞" "一般" "*" "*" "*"]]
 ["、" ["補助記号" "読点" "*" "*" "*" "*"]]
 ["そして" ["接続詞" "*" "*" "*" "*" "*"]]
 ["万物" ["名詞" "普通名詞" "一般" "*" "*" "*"]]
 ["に" ["助詞" "格助詞" "*" "*" "*" "*"]]
 ["つい" ["動詞" "一般" "*" "*" "五段-カ行" "連用形-イ音便"]]
 ["て" ["助詞" "接続助詞" "*" "*" "*" "*"]]
 ["の" ["助詞" "格助詞" "*" "*" "*" "*"]]
 ["究極" ["名詞" "普通名詞" "一般" "*" "*" "*"]]
 ["の" ["助詞" "格助詞" "*" "*" "*" "*"]]
 ["疑問" ["名詞" "普通名詞" "一般" "*" "*" "*"]]
 ["の" ["助詞" "格助詞" "*" "*" "*" "*"]]
 ["答え" ["名詞" "普通名詞" "一般" "*" "*" "*"]]]
```

Note that the `sudachi-clj.core/analyze` function returns nil if the system is not started or stopped.

After completing the morphological analysis, it's recommended to shut down the system to free resources, using `sudachi-clj.core/stop`.

```
sudachi-clj.core=> (stop)
:stopped
```

### Settings
The settings passed to the original Java Sudachi library are defined at `:sudachi-clj.config/json` in `resources/config.edn`. If you want to change the plug-in settings and so on, look out there.

See also: https://github.com/WorksApplications/Sudachi

## License

Copyright © 2019 sandmark

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
