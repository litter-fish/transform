# transform

### 程序功能###
将word、excel、ppt、pdf、rtf、csv等格式转换成html、txt、pdf、image

### 使用说明###
程序入口: ConverFile.converFile


### 2016-07-22 新增csv文件转换模块###
新增CSVFactory类

### 2016-07-24 调整类之间关系####
将产品的创建放置具体的产品类中，产品工厂类只负责调用具体产品创建对象


### 2016-07-20 目前存在问题 ###
1. ppt/pptx等格式转换速度慢
2. xlsx文件转换过程中有些样式会丢失
3. docx文件转换格式丢失

