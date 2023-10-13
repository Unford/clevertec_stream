# clevertec_stream
[Задание](task.txt)

### Задачи
- 1 - 12  реализованы в [PersonServiceImpl.java](src/main/java/ru/clevertec/course/stream/service/impl/PersonServiceImpl.java)
- (В заданиях с выводом текста, возвращается список, по заданию нужно было использовать .forEach(System.out::println) вместо toList())
- 13 - 16 в [CustomUtils.java](src/main/java/ru/clevertec/course/stream/util/CustomUtils.java)


| Benchmark                | Iterations | Mode | Cnt | Score     | Units |
|--------------------------|------------|------|-----|-----------|-------|
| benchmarkSequenceSum     | 1000000    | avgt | 2   | 15.776    | ms/op |
| benchmarkSequenceSum     | 10000000   | avgt | 2   | 157.322   | ms/op |
| benchmarkSequenceSum     | 100000000  | avgt | 2   | 1576.455  | ms/op |
| benchmarkTwoParallelSum  | 1000000    | avgt | 2   | 54.721    | ms/op |
| benchmarkTwoParallelSum  | 10000000   | avgt | 2   | 556.486   | ms/op |
| benchmarkTwoParallelSum  | 100000000  | avgt | 2   | 5452.798  | ms/op |
| benchmarkFourParallelSum | 1000000    | avgt | 2   | 104.088   | ms/op |
| benchmarkFourParallelSum | 10000000   | avgt | 2   | 1074.948  | ms/op |
| benchmarkFourParallelSum | 100000000  | avgt | 2   | 10743.921 | ms/op |



