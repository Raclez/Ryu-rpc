# Ryu-rpc
#一个简单rpc框架目前有集成spring使用注解简单使用和没有集成spring两个版本

- 因为netty如此优秀所以选择使用 Netty（基于 NIO实现网络传输)
- 使用可插拔的序列化机制（java,kyro(默认),hession,protostuff...）
- 注册中心使用redis,zookeeper以及nacos
- 集成spring容器自动配置
- 模仿dubbo的SPI机制
- 多种负载均衡散发
- 协议是自定义协议（准备多个协议）
- 动态代理使用的java自带的
 
 
 
 
 ![image]( https://educa-10.oss-cn-beijing.aliyuncs.com/2023-04-10/rpc.png)
 
 
