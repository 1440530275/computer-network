/**
 * 在java代码中，封装的代码已经实现了相关的ICMP协议, 由于java只能调用socket进行应用层协议处理，
 * 所以无法进行手动编写一份，但是ICMP的协议内容相对简单
 * <a link="https://en.wikipedia.org/wiki/Internet_Control_Message_Protocol"/>
 *
 * {@link java.net.Inet4AddressImpl#isReachable} 可以直接调用
 */
package com.haolong.chapter2.ICMP;