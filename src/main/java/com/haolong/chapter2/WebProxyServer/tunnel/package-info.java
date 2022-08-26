/**
 * 由于https和http代理方式完全不一样，所以在这里单独对https进行了处理， 其实可以进行整合在一起，但是为了学习的便利性，所以进行分开处理
 * channel使用的nio模型进行通道进行代理
 * socket使用的原始的socket进行通道代理
 */
package com.haolong.chapter2.WebProxyServer.tunnel;