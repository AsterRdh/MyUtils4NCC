# 这是一个为NCC二开的工具类、集合和语法糖的集合
## 使用方法
将jar包放入ncchome\external\lib目录下
## 工具类和语法糖
1. CollectionUtils 数组、集合的一些常用方法
2. SplitAndMergeFile 分割、合并文件的工具，比如将大文件存入文件服务器或者多线程分发文件时可以使用
3. ZipCompressService 缩成解压zip文件
4. DateUtils 时间日期相关工具
5. NumberUtils 数字相关工具
6. ObjectUtils 对象相关工具
7. StringUtils 字符串相关工具
8. MathUtils 针对UFDouble的数学工具
9. SqlBuilder sql构造器，新版本开发中
10. ExcelUtils Excel工具类，目前只有 列数和Excel列互转
11. CellBuilder poiExcel的行列构建器
12. StyleBuilder poiExcel的单元格格式构建器

## 集合
### 1. MapList<K,V>
封装 HashMap<K,ArrayList<V>>
### 2. MapSet<K,V>
封装 HashMap<K,HashSet<V>>
### 3. MapStack<K,V>
封装 HashMap<K,Stack<V>>
### 3. MapMap<K1,K2,V>
封装 HashMap<K1,Map<K2,V>>
### 4. MapMapList<K1,K2,V>
封装 HashMap<K1,MapList<K2,V>>
### 5. MapMapSet<K1,K2,V>
封装 HashMap<K1,MapSet<K2,V>>
### 6. MapMapStack<K1,K2,V>
封装 HashMap<K1,MapStack<K2,V>>
### 7. Map3<K1,K2,,K3,V>
封装 HashMap<K1,MapMap<K2,K3,V>>
### 8. Map3List<K1,K2,,K3,V>
封装 HashMap<K1,MapList<K2,K3,V>>
### 9. Map3Stack<K1,K2,K3,V>
封装 HashMap<K1,MapStack<K2,K3,V>>
### 10. RangeMap <K,V> 
范围Map，不适合处理跨度较大的数  
示例见 space.cyberaster.utils.range.App



