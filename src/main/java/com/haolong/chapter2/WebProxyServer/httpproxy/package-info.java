/**
 * 使用httpProxy来进行代理请求，即用户发送http请求到该代理服务器端口, 然后服务器进行读取响应的请求头和报文数据进行转发到对应的源服务器。
 * 整个流程对应的是:
 *
 * user agent  ----->  proxy server  -------->  original server
 *                     read header and body
 *
 * 但是该代理带来的一个问题是, 如果通过这种代理进行转发请求，当代理服务器不是一个可信的服务器时候，所有的数据都将被探测，不管是使用HTTP还是HTTPS。
 * 因为即使是HTTPS的代理, 也可以通过安装可信的证书来进行解密
 *
 * 这样做的坏处是需要写一些解析的代码和一些逻辑性的东西，但是好处是如果你是一个坏蛋，你就可以解析数据拿到明文数据，甚至可以写一个简单的策略，
 * 如果在内容中包含password或一些敏感的词汇, 可以进行解析，然后存储。是不是很可怕呢。
 */
package com.haolong.chapter2.WebProxyServer.httpproxy;