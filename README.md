# clevertec_stream

[Задание](task.txt)

### Задачи

- 1 - 12 реализованы
  в [PersonServiceImpl.java](src/main/java/ru/clevertec/course/stream/service/impl/PersonServiceImpl.java)
- (В заданиях с выводом текста, возвращается список, по заданию нужно было использовать .forEach(System.out::println)
  вместо toList())
- 13 - 16 в [CustomUtils.java](src/main/java/ru/clevertec/course/stream/util/CustomUtils.java)

| Benchmark                | Iterations  | Mode | Count | Score    | Units |
|--------------------------|-------------|------|-------|----------|-------|
| benchmarkSequenceSum     | 10,000      | avgt | 2     | 0.158    | ms/op |
| benchmarkSequenceSum     | 1,000,000   | avgt | 2     | 15.806   | ms/op |
| benchmarkSequenceSum     | 10,000,000  | avgt | 2     | 157.876  | ms/op |
| benchmarkSequenceSum     | 100,000,000 | avgt | 2     | 1579.564 | ms/op |
| benchmarkTwoParallelSum  | 10,000      | avgt | 2     | 0.589    | ms/op |
| benchmarkTwoParallelSum  | 1,000,000   | avgt | 2     | 55.097   | ms/op |
| benchmarkTwoParallelSum  | 10,000,000  | avgt | 2     | 564.467  | ms/op |
| benchmarkTwoParallelSum  | 100,000,000 | avgt | 2     | 5627.712 | ms/op |
| benchmarkFourParallelSum | 10,000      | avgt | 2     | 1.294    | ms/op |
| benchmarkFourParallelSum | 1,000,000   | avgt | 2     | 93.587   | ms/op |
| benchmarkFourParallelSum | 10,000,000  | avgt | 2     | 551.247  | ms/op |
| benchmarkFourParallelSum | 100,000,000 | avgt | 2     | 4933.180 | ms/op |

Score - чем меньше тем лучше

