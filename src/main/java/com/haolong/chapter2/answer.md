### 2.1节

**R1**.

WEB[HTPP]、 XFTP[FTP]、DUBBO[DUBBO]、e-mail[SMTP]、 BitTorrent[BitTorrent]

**R2**.

应用程序体系结构是在网络体系结构之上的。 应用程序是五层结构， 在规范中已经定义好的。但是应用程序是由应用人员进行开发设计的。

**R3**.

发起通讯的进程被标识为**客户**， 在会话开始等待连接的是**服务器**。

**R4**.

不同意， 发起通信会话的一方总是客户， 接收通讯的一方是服务端。

**R5**.

IP地址和port端口来标识另一台主机上的进程。

**R6**.

我会使用UDP协议， 因为UDP协议无需握手。在一次往返时间内就可以获得结果， 但是使用TCP需要进行两次往返时间。一次用于握手时间。

**R7**.

双11， 双11是一个要求数据不会丢失并且要求时间高度敏感的应用， 因为需要进行抢购。但是考虑到各种因素 ， 所以并不一定在时间上有保证。

**R8**.

- 可靠数据传输(TCP)
- 吞吐量(都没有)
- 定时(都没有)
- 安全(都没有) 但是SSL提供了安全性， Secure Socket Layer,安全层套接字。

**R9**.

SSL运行在应用层， 如果说UDP也想使用加密措施的话， 那么就需要在传输给socket前面将使用SSL的加密策略。

**R10**.

确认身份,并且可以知道双方的接受和发送能力是否都是正常的。

**R11**.

数据传输可靠性。

**R12**.

首先用户进入网站， 为该用户生成一个该客户对应的唯一cookie, 每次该客户进行购买物品的时候就会记录cookie对应的商品记录。该cookie值永远有效。但是这种做法不是很好， 因为cookie是存储在客户端， 有可能被用户直接删除， 最好的做法是让用户注册账号这样进行数据关联。

**R13**.

详情看p72.

首先WEB缓存是一个代理服务器， 可以使NGINX， 也可以是tomcat等服务器， 首先， 浏览器或者其他的客户端进行发起HTTP请求到缓存服务器， 缓存服务器进行查找该缓存资源是否存在， 如果不存在， 那么请求服务器资源进行返回给客户端。

只能减少某些资源。

**R14**.


```linux
telnet www.baidu.com 80
Trying 36.152.44.96...
Connected to www.a.shifen.com.
Escape character is '^]'.
GET /img/flexible/logo/pc/peak-result.png HTTP/1.1
Host: www.baidu.com
If-modified-since: Fri, 12 Nov 2021 23:01:00 GMT

HTTP/1.1 304 Not Modified
Cache-Control: max-age=315360000
Date: Wed, 17 Nov 2021 15:02:24 GMT
Etag: "1e1b-5b00622d17d00"
Expires: Sat, 15 Nov 2031 15:02:24 GMT
Server: Apache
Set-Cookie: BAIDUID=CBCAE22E3D1ABC58E695E2C8DE491BB8:FG=1; expires=Thu, 17-Nov-22 15:02:24 GMT; max-age=31536000; path=/; domain=.baidu.com; version=1
```

**R15**.
微信和QQ。 不是使用相同的运输层协议， 微信[TCP]， QQ[UDP]。

**R16**.

```
        http                        SMTP
Alice ---------> Alice邮件服务器 ----------->
            POP3
Bob邮件 ----------> Bob
```

使用了HTTP， SMTP， POP3

**R17**.

每封邮件收到一行 receiveed: 首部行。

```
eceived: from WS-web (postmaster@alibaba-inc.com[ma_---.ErYwx8s]) by e02c03279.eu6 at Sat, 29 Jun 2019 19:48:09 +0800
Date: Sat, 29 Jun 2019 19:48:09 +0800
From: "Alimail " <postmaster@alibaba-inc.com>
To:  <weihaolong@v1010.cn>
Message-ID: <7cf792b2-eac0-4a08-85d0-3342e29ea208.postmaster@alibaba-inc.com>
Subject: =?UTF-8?B?44CQ6Zi/6YeM5LqR6YKu44CR6YKu566x6LSm5Y+357uR5a6a5omL5py65Y+356CB5o+Q6YaS?=
  =?UTF-8?B?Cg==?=
X-Mailer: [Alimail-Mailagent]
MIME-Version: 1.0
x-aliyun-special-mail-param: ewogICAgIiRQaG9uZU51bWJlciQiOiAiMDA4KioqKjc5MjQ5NjM5IiwKICAgICIkVGltZSQiOiAiMTU2MTgwODg4OCIsCiAgICAic2lnbiI6ICI2NmNmMDBlNGQwNTBiZWIwMWZkYzNmZDUzMGE4NmY4MiIsCiAgICAic3VidHlwZSI6ICJmcmVlLm5ldC5jbiIsCiAgICAidHlwZSI6ICJ0eXBlX2NlbGxwaG9uZV9iaW5kaW5nIn0=
Content-Type: multipart/alternative;
  boundary="----=ALIBOUNDARY_51002_4d7e3940_5d174ff9_12992c4"

------=ALIBOUNDARY_51002_4d7e3940_5d174ff9_12992c4
Content-Type: text/plain; charset="UTF-8"
Content-Transfer-Encoding: base64

...
```


**R18**.

- 下载并删除

  如果在当前电脑上有登陆邮箱地址， 并且收到了一封邮件， 那么这个邮件将会在服务器中删除， 另一台电脑将无法同步该邮件。

- 下载并保留

  同理， 如果在当前电脑上收到了一封邮件， 那么在另外一台电脑上可以进行邮件同步操作。并且在一台新电脑上也可以进行邮件同步。

**R19**.

可以拥有完全相同的主机名别名， 因为类型不同。 一个是A类型， 一个是MX类型。

**R20**.

可以从报文首部看到包含edu的邮箱地址。 但是使用gmail是无法看到的。

**R21**.

不需要， Alice可能不是Bob的前4个邻居。

**R22**.

等待其他对等方随机在路由地址表中进行挑选， 此时就有机会进行收到数据并且进行数据交换。

**R23**.

覆盖网络就是对等方链路中包括的网络。(不官方)

不包括路由器， 一个对等方到另一个对等方的路径。

**R24**.

- 深入

  深入到ISP(intenet service provider)中， 在这个位置部署服务器集群。其目标是靠近端用户， 通过减少端用户和CND集群之间的链路和路由器的数量。但是这种高度分布式设计， 维护是一项比较巨大的挑战。


- 邀请做客

  该原则是通过少量的关键位置建造超大集群来邀请ISP做客。不是将集群放在ISP中， 通常是在IXP中。

**R25**.

负载均衡， 不能让某一个地方的CDN节点超负荷。例如因为地区的不同可能美国是白天而中国是黑夜， 可能导致负载差异较大。这时候可以做出一些策略进行调整。

**R26**.

因为TCP的一个套接字是用于握手。UDP不需要进行握手。

如果TCP支持n个并行的连接， 那么TCP服务器将需要n+1个套接字。

**R27**.

TCP, 需要事先建立连接， 也就是握手

UDP， 不需要。

### 习题

**P1**

a. 错 b. 对 c. 错 d.错 e. 错

**P2**.

- SMS(Short Messaging Service短信服务)

SMS协议

即：短信服务。是最早的短消息业务，也是现在普及率最高的一种短消息业务，通过它移动电话之间可以互相收发短信，内容以文本、数字或二进制非文本数据为主，这种短消息的长度被限定在140字节之内。SMS以简单方便的使用功能受到广大用户的欢迎，迅速普及，但却始终是属于第一代的无线数据服务，在内容和应用方面存在技术标准的限制。

- iMessage

iMessage是苹果公司推出的即时通信软件，可以发送短信、视频等，其拥有非常高的安全性。不同于运营商短信/彩信业务，用户仅需要通过WiFi或者蜂窝数据网络进行数据支持，就可以完成通信。

- WhatsApp

是一款可供iPhone手机、Android手机、Windows Phone手机、WhatsApp Messenger、Symbian手机和 Blackberry 黑莓手机用户使用的、用于智能手机之间通讯的应用程序。本应用程序借助推送通知服务，可以即刻接收亲友和同事发送的信息。可免费从发送手机短信转为使用WhatsApp程序，以发送和接收信息、图片、音频文件和视频信息。

==iMessage和WhatsApp与SMS不同，因为它们使用数据计划发送消息，并在TCP/IP网络上工作，但SMS使用我们从无线运营商购买的文本消息计划。此外，iMessage和WhatsApp支持发送照片、视频、文件等，而原始短信只能发送短信。最后，iMessage和WhatsApp可以通过WiFi工作，但是SMS不能。==

**P3**.

DNS[UDP]、 HTTP[TCP]

**p4**.

a. http://gaia.cs.umass.edu/cs453/index.html

b. HTTP/1.1

c. keep-alive

d. 在http中看不见自己的ip地址

e. Mozilla/5.0。 因为服务器需要知道浏览器的不同类型和版本的信息。

**P5**.

a. 能。 Tue 07 Mar 2008

b. Sat 10 Doc2005 18:27:46

c. 3847

d. !doct 同意。 max = 100

**p6**.

a. 能， connection:close

b. Http没有提供加密服务， https协议使用SSL加密算法。

c. 使用持续连接的客户端应限制与某一服务器同时连接的个数。单用户客户端不应与任一服务器或代理服务器保持两个以上的连接。代理服务器与其它服务器或代理服务器之间应维护2*N个连接，其中N是同时在线的用户数。这些准则是为了改善响应时间和和避免阻塞。

d. 对。（来自RFC 2616）“客户端可能在服务器决定关闭“空闲”连接的同时开始发送新请求。从服务器的角度来看，连接处于空闲状态时正在关闭，但从客户端的角度来看，请求正在进行中。”

**p7**.

DNS: `$RTT_1 + RTT_2 + ... RTT_n$`

TCP: `$RTT_0$`

HTTP: `$RTT_0$`

`$2RTT_0 + RTT_1 + RTT_2 + ... RTT_n$`

**p8**.

a. `$16RTT_0 + 2RTT_0 + RTT_1 + RTT_2 + ... RTT_n$`

b. `$2RTT_0 + 4RTT_0 + RTT_1 + RTT_2 + ... RTT_n$`

c. `$2RTTP_0 + 8RTT_0 + RTT_1 + RTT_2 + ... RTT_n$`

**p9**.

已知：

1. 3秒因特网时延
2. `$\tfrac{x}{1-(xy)}$`
3. x:跨越接入链路发送一个对象平均时间
4. y: 对对象介入链路的平均到达率

a.

```math

\tfrac{850000}{15000000}=0.0567(ms)

\tfrac{0.0567}{1-(0.0567*16)}=0.6(s)

0.6 + 3 = 3.6(s)

```

b.

因为有40%的缓存命中率， 所以只有60%会跨越因特网。

```math

    \tfrac{0.0567}{1-(0.6 * 0.0567*16)}
    
    
    \tfrac{0.0567}{0.4558} = 0.1244(s)
    
    0.6 * 3.1244 = 1.87(s)
```

**p10**.

没有意义， 没有很大增益， 但是也会有一点点效果。

**p11**.

a. 能， 但是效果不明显

b. 有， 能省去握手的时间

**p12**.

```
##socket inputStream数据

GET / HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
Accept-Encoding: gzip, deflate, br
Accept-Language: zh-CN,zh;q=0.9

```

**p13**.

SMTP中的 MAIL FROM 是表示客户端的邮件地址， 但是报文中的 From: 是报文内容

**p14**.

SMTP 是以 “.” 为结束的符号。 而HTTP采用content-length 表示报文的长度。不能， SMTP使用的是7位的ASCLL表示。

**p15**.

MTA (mail transfer agents)

**p16**.

UIDL (unique id listing) 唯一id列表。 RFC1939 P12

UIDL缩写为“唯一ID列表”。当POP3客户机发出UIDL命令时，服务器使用用户邮箱中所有邮件的唯一邮件ID进行响应。这个命令对“下载并保存”很有用。通过维护一个列出在早期会话期间检索到的消息的文件，客户端可以使用UIDL命令来确定服务器上已经看到了哪些消息。

**p17**.


```
C: list S: 1 498
S: 2 912
S: .
C: retr 1
S: blah ..... S: ....blah S: .
C: retr 2
S: blah blah ...
S: ...........blah
S: .
C: quit
S: +OK POP3 server signing off
```


**p18**.

a. whois（读作“Who is”，非缩写）是用来查询域名的IP以及所有者等信息的传输协议。简单说，whois就是一个用来查询域名是否已经被注册，以及注册域名的详细信息的数据库（如域名所有人、域名注册商）。通过whois来实现对域名信息的查询。早期的whois查询多以命令列接口存在，但是现在出现了一些网页接口简化的线上查询工具，可以一次向不同的数据库查询。网页接口的查询工具仍然依赖whois协议向服务器发送查询请求，命令列接口的工具仍然被系统管理员广泛使用。whois通常使用TCP协议43端口。每个域名/IP的whois信息由对应的管理机构保存。

b. 站长工具

qq.com 的 dns 解析为 NS1.QQ.COM

alibaba.com 的 dns 解析为 NS1.ALIBABADNS.COM

c.

```
domain: qq.com

web service:

Address: 58.250.137.36
Name:	qq.com
Address: 58.247.214.47
Name:	qq.com
Address: 125.39.52.26

mail server:

qq.com	mail exchanger = 20 mx2.qq.com.
qq.com	mail exchanger = 30 mx1.qq.com.
qq.com	mail exchanger = 10 mx3.qq.com.

name server:

qq.com	nameserver = ns4.qq.com.
qq.com	nameserver = ns1.qq.com.
qq.com	nameserver = ns2.qq.com.
qq.com	nameserver = ns3.qq.com.

```

d.所在的学校没有多个ip地址

e. 首先通过nslookup查询学校官网地址。

后面https://whois.arin.com.haolong.net/ 对该解析地址进行查询。<br/>
Net Range: 111.0.0.0 - 111.255.255.255


f. 使用nslookup 和 whois 工具进行获取ip范围和dns解析地址， 从而进行攻击。通过分析攻击包的源地址，受害者可以使用whois获取攻击来自的域的信息，并可能通知源域的管理员。

g. 因为当某公司或者个人需要申请地址的时候， 需要查看该地址是否被注册。

**p19**.

a.
```
dig +trace @a.root-servers.com.haolong.net v1010.cn

; <<>> DiG 9.9.7-P3 <<>> +trace @a.root-servers.com.haolong.net v1010.cn
; (1 server found)
;; global options: +cmd
.			518400	IN	NS	a.root-servers.com.haolong.net.
.			518400	IN	NS	b.root-servers.com.haolong.net.
.			518400	IN	NS	c.root-servers.com.haolong.net.
.			518400	IN	NS	d.root-servers.com.haolong.net.
.			518400	IN	NS	e.root-servers.com.haolong.net.
.			518400	IN	NS	f.root-servers.com.haolong.net.
.			518400	IN	NS	g.root-servers.com.haolong.net.
.			518400	IN	NS	h.root-servers.com.haolong.net.
.			518400	IN	NS	i.root-servers.com.haolong.net.
.			518400	IN	NS	j.root-servers.com.haolong.net.
.			518400	IN	NS	k.root-servers.com.haolong.net.
.			518400	IN	NS	l.root-servers.com.haolong.net.
.			518400	IN	NS	m.root-servers.com.haolong.net.
.			518400	IN	RRSIG	NS 8 0 518400 20200214050000 20200201040000 33853 . aFkcOXQYjErpT9Itq1hNYzqKDpROX+vFx7ytObxrrzZUwjipCF9xWCdZ W+OwUfOAr8UkUUSkDMwZQx94Y2CndnmjUCP4KLdvdb1Ak8EjZjOm0Lfm erUyYkhJrICudq0pQjZZA2509QTtTuY+mqq0CGv9+ycNTU6c2C+D3duQ u96vuZ5xBf/Djoqjo3eJraLViqiG+2eTO/pUSNKLXj/F5jU8Kfm0Qc6C 8nqkJyGP+UkEssOM4E+Kq9W1/XNxyXehXXk5xn1kWRHco5P8fbre7BPa ItkooRQlqA12VatFu8S65rh15EBtw4Yrgg5GcRliaKXdzMZ07sCcY4sD UxpeAQ==
;; Received 1097 bytes from 198.41.0.4#53(a.root-servers.com.haolong.net) in 397 ms

cn.			172800	IN	NS	ns.cernet.com.haolong.net.
cn.			172800	IN	NS	f.dns.cn.
cn.			172800	IN	NS	g.dns.cn.
cn.			172800	IN	NS	e.dns.cn.
cn.			172800	IN	NS	b.dns.cn.
cn.			172800	IN	NS	a.dns.cn.
cn.			172800	IN	NS	d.dns.cn.
cn.			172800	IN	NS	c.dns.cn.
cn.			86400	IN	DS	57724 8 2 5D0423633EB24A499BE78AA22D1C0C9BA36218FF49FD95A4CDF1A4AD 97C67044
cn.			86400	IN	RRSIG	DS 8 1 86400 20200214050000 20200201040000 33853 . a2aE7RNn7QH/94sUjCH0fs5le6FEeE0DtH7A8XaIBNaBNqAeqRK/AUfV p/yRHr0zSJqLxTl2JHIjzSA2S3p2I1Yu5KDfNT5IPy8e2cz2+QIoymid JwDX23YOr8Q8BvWFi+3rkr20kisFJrv3MIF7nK4VxAWbPng8bbA8LclA 9CIU+aULbFzBL8E8IiCb1orMr3eLPICSKNC9z+YH2k5U5Ij9RCGGlODI jdRCnkNLIEv72ZlFbtaTZNNWCaK+cDNX7SlIiodmBm9dUBb2bW10spo4 cZ0QdBuGnWrdmCTJFbPGpoEWxoh5DpvAGOQyBySjL99HcgHkkdAgeIdg 66Ahsg==
;; Received 699 bytes from 202.12.27.33#53(m.root-servers.com.haolong.net) in 46 ms

v1010.cn.		86400	IN	NS	dns26.hichina.com.
v1010.cn.		86400	IN	NS	dns25.hichina.com.
3QDAQA092EE5BELP64A74EBNB8J53D7E.cn. 21600 IN NSEC3 1 1 10 AEF123AB 3QLMP0QRNQ96G5AFGOPNB7U7IJ4MBP4B NS SOA RRSIG DNSKEY NSEC3PARAM
3QDAQA092EE5BELP64A74EBNB8J53D7E.cn. 21600 IN RRSIG NSEC3 8 2 21600 20200215060850 20200116052740 38388 cn. huPnycbAa2Vb4xJLfCbkBVhCAQ9uk2xzyqhzFiOzq7CKrDK3oMw7APOx YSnjPdn4v63iuq6y4A7NHCYpSYgFMcoKk5jXMmxgeRZPLi8F5ophcgMZ 0DfwYtlAEdJBC95PncynRoLzmBsZWP/qZrfALz4eNv0KYfOxPO5omfSz e/Q=
NGC71A92VM3U5HJ8U37EC66JNH5D82FV.cn. 21600 IN NSEC3 1 1 10 AEF123AB NGHP93D47UCCEMFOR65TBMHUK0UBUMM6 CNAME RRSIG
NGC71A92VM3U5HJ8U37EC66JNH5D82FV.cn. 21600 IN RRSIG NSEC3 8 2 21600 20200215064426 20200116061624 38388 cn. FRU57jehpNCZWqmvpX/Q7tAFLpf5ntHEEO9kgq+Ehe8BFWCpXJyGnJvB dHbuv9ZAwA2e3LLeJyVDiip5CpQs5Gekl1xytmiXJ86H7NAPmjkXJaNi qkaA0LH+3hNQ4MLechpXHvPKuQnpRo1ajlUX+ypiNak5y7N6VuzidAsB 1Tc=
;; Received 579 bytes from 195.219.8.90#53(f.dns.cn) in 444 ms

v1010.cn.		600	IN	SOA	dns25.hichina.com. hostmaster.hichina.com. 2019062700 3600 1200 86400 360
;; Received 112 bytes from 106.11.211.69#53(dns25.hichina.com) in 40 ms
```


**p20**.

我们可以定期获取本地DNS服务器中DNS缓存的快照。在DNS缓存中出现最频繁的Web服务器是最流行的服务器。这是因为如果更多的用户对一个Web服务器感兴趣，那么该服务器的DNS请求将更频繁地由用户发送。因此，该Web服务器将更频繁地出现在DNS缓存中。

有关完整的测量研究，请参见：

克雷格·E·威尔斯，米哈伊尔·米哈伊洛夫，郝尚

“通过主动查询DNS缓存推断Internet应用程序的相对流行度”，2003年10月27-29日，IMC'03，美国佛罗里达州迈阿密海滩

**p21**.

可以， 如果使用了dns查询的话， 那么它会在本地缓存中，那么下次就不会进行查询， 直接读取本地路径进行请求。查询的时间几乎为0.

**p22**.


```math

D_{cs} = max\{\tfrac{FN}{u_s}, \tfrac{FN}{d{min}}\}

D_{p2p} = max\{\tfrac{F}{u_s}, \tfrac{F}{d{min}}, \tfrac{NF}{u_s + \sum^n_{i=1}ui}\}

```

**p23**.

a. 假定 `$\tfrac{u_s}{N} <= d_{min}$`。 因为平均的的分发速率也没有超过`$d_{min}$`, 所以分发时间为 `$\tfrac{F}{\tfrac{us}{N}}$` = `$\tfrac{FN}{us}$`

b. 假定 `$\tfrac{u_s}{N} >= d_{min}$`。 因为平均的分发速率大于最小的客户端速率， 因此， 最大的分发时间为 `$\tfrac{F}{n_{min}}$`

c. 由以上a 和 b 可以得出结论， 当速率大于或者小于`$d_{min}$`时， 最大的时间节点为

`$max\{\tfrac{FN}{u_s}, \tfrac{FN}{d{min}}\}$`

**p24**.

考虑


**p25**.

有n个节点和 n(n-1) * 2条边。

**p26**.

可以， Bob可以通过其他对等方随机挑选到自己进行下载文件。

他的第二个说法也是正确的。他可以在每台主机上运行一个客户机，让每台客户机“自由运行”，并将从不同主机收集的数据块组合成一个文件。他甚至可以编写一个小的调度程序，让不同的主机请求不同的文件块。这实际上是P2P网络中的一种Sybil攻击。

**p27**.

N, 低质量的视频文件和低质量的音频文件在一起。

2N， 音频文件和视频文件进行分开。

**p28**.

a. 连接失败， 因为TCPserver没有启动， 所以无法进行连接。

b. 可以正常的运行.因为不需要和UCPserver连接。

c. 如果使用不同的端口号，则客户端将尝试使用错误的进程或不存在的进程建立TCP连接。将发生错误。

**p29**.

不需要更改端口号， 在UDPClient中发送数据的时候， UDPServer 中从数据包中可以看到该端口号是多少， 从而得到ip信息和端口号进行返回数据。

UDPClient默认可以不指定端口号， 随机开启端口号。因此UDPServer不需要改变端口号。

**p30**.

可以设置浏览器对某个站点的并行的连接数， 大量使用TCP并行连接的优点是在带宽足够的情况下速度会更快， 但是缺点是会对服务器和自己的客户端造成大量的负载。并且会占用大量的带宽。

**p31**.

面向无连接的 UDP协议是面向报文的有边界的报文的协议。发送方的UDP对应用程序交下来的报文，在添加首部后就向下交付给IP层。既不拆分，也不合并，而是保留这些报文的边界，因此，应用程序需要选择合适的报文大小。

面向连接的TCP协议属于无边界的字节流协议，用户每次调用接收发送函数接口时，不一定都能接收发送一条完整的消息，而是必须对裸字节流进行拆分、组合（同于基于有边界报文的UDP协议的应用程序有很大差别）。

read/recv/recvfrom：TCP，客户端连续发送数据，只要服务端的这个函数的缓冲区足够大，会一次性接收过来（客户端是分好几次发过来，是有边界的，而服务端却一次性接收过来，所以证明是无边界的）；

UDP：客户端连续发送数据，即使服务端的这个函数的缓冲区足够大，也只会一次一次的接收，发送多少次接收多少次（客户端分几次发送过来，服务端就必须按几次接收，从而证明，这种UDP的通讯模式是有边界的）。

**P32**.

Apache Web是一款web软件， 是一个应用在主机上的程序。 是开源免费的， 主要是进行Web应用。


## Wireshark实验： HTTP

**1.基本HTTP GET/response交互**

启动浏览器， 访问 http://gaia.cs.umass.edu/wireshark-labs/HTTP-wireshark-file1.html 地址显示非常简单的HTML文件， 然后停止Wireshark捕获， 查看文件。

![](https://s2.ax1x.com/2020/02/15/1zKsC4.png)

在这张图片中， 只查看了HTTP的请求， 可以发现， 有4个请求。 两个request请求和两个response请求。 只需查看访问上面地址的请求。 不用去管/favicon.ion的请求。

** 请求报文： **
- request method: GET

- request uri:/wireshark-labs/HTTP-wireshark-file1.html

- requewst version: HTTP/1.1

- accept-Language: zh-CN,zh;q=0.9

- 通过界面可以看到 source： 192.168.0.102 destination: 128.119.245.12

**返回报文：**

- request version: HTTP/1.1

- request status: 200 OK

- Last-Modified: Sat, 15 Feb 2020 06:59:01 GMT

- content-length: 128


**2.HTTP条件Get/response交互**

- 启动您的浏览器，并确保您的浏览器的缓存被清除，如上所述。
- 启动Wireshark数据包嗅探器。
- 在浏览器中输入以下URL http://gaia.cs.umass.edu/wireshark-labs/HTTP-wireshark-file2.html 您的浏览器应显示一个非常简单的五行HTML文件。
- 再次快速地将相同的URL输入到浏览器中（或者只需在浏览器中点击刷新按钮）。
- 停止Wireshark数据包捕获，并在display-filter-specification窗口中输入“http”，以便只捕获HTTP消息，并在数据包列表窗口中显示。

回答下列问题：

- 检查第一个从您浏览器到服务器的HTTP GET请求的内容。您在HTTP GET中看到了“IF-MODIFIED-SINCE”行吗？

从第一个请求中没有看到 if-modified-since行， 但是第二次刷新之后看到了if-modified-since行内容。

- 检查服务器响应的内容。服务器是否显式返回文件的内容？ 你是怎么知道的？

第一次有响应报文体和content-length。 但是第二次是没有的。并且是304 状态。

- 现在，检查第二个HTTP GET请求的内容。 您在HTTP GET中看到了“IF-MODIFIED-SINCE:”行吗？ 如果是，“IF-MODIFIED-SINCE:”头后面包含哪些信息？

看到了 if-modified-since 行的信息， 信息是第一次返回的 last-modified 的时间， 表示请求服务器这个文件在这个时间段之后是否有更新， 如果有， 就不会是304状态了。

- 针对第二个HTTP GET，从服务器响应的HTTP状态码和短语是什么？服务器是否明确地返回文件的内容？请解释。

request status : 304 not modified。

**3.检索长文件**

- 启动您的浏览器，并确保您的浏览器缓存被清除，如上所述。
- 启动Wireshark数据包嗅探器
- 在您的浏览器中输入以下URL http://gaia.cs.umass.edu/wireshark-labs/HTTP-wireshark-file3.html 您的浏览器应显示相当冗长的美国权利法案。
- 停止Wireshark数据包捕获，并在display-filter-specification窗口中输入“http”，以便只显示捕获的HTTP消息。

在分组列表窗口中，您应该看到您的HTTP GET消息，然后是对您的HTTP GET请求的多个分组的TCP响应。这个多分组响应值得进行一点解释。回顾第2.2节（见文中的图2.9），HTTP响应消息由状态行组成，后跟标题行，后跟一个空行，后跟实体主体。在我们的HTTP GET这种情况下，响应中的实体主体是整个请求的HTML文件。在我们的例子中，HTML文件相当长，4500字节太大，一个TCP数据包不能容纳。因此，单个HTTP响应消息由TCP分成几个部分，每个部分包含在单独的TCP报文段中（参见书中的图1.24）。在Wireshark的最新版本中，Wireshark将每个TCP报文段指定为独立的数据包，并且单个HTTP响应在多个TCP数据包之间分段的事实由Wireshark显示的Info列中的“重组PDU的TCP段”指示。 Wireshark的早期版本使用“继续”短语表示HTTP消息的整个内容被多个TCP段打断。我们在这里强调，HTTP中没有“继续”消息！

回答下列问题：

![image](https://s2.ax1x.com/2020/02/16/3p2fje.md.png)

- 您的浏览器发送多少HTTP GET请求消息？哪个数据包包含了美国权利法案的消息？

浏览器发送1条HTTP GET的消息。返回的response里面包含了美国权利法案的消息。

- 哪个数据包包含响应HTTP GET请求的状态码和短语？

第一个TCP的数据包当中包含了状态码和短语信息

- 响应中的状态码和短语是什么？

HTTP/1.1 200 OK

- 需要多少包含数据的TCP段来执行单个HTTP响应和权利法案文本？

目前的看到在我当前的机器下一个包里面能够容纳的报文数据是 1428位， 所以需要用4此TCP段来执行单个的HTTP响应。

**4.具有嵌入对象的HTML文档**

- 启动您的浏览器。
  启动Wireshark数据包嗅探器。
- 在浏览器中输入以下URL http://gaia.cs.umass.edu/wireshark-labs/HTTP-wireshark-file4.html 您的浏览器应显示包含两个图像的短HTML文件。这两个图像在基本HTML文件中被引用。也就是说，图像本身不包含在HTML文件中；相反，图像的URL包含在已下载的HTML文件中。如书中所述，您的浏览器将不得不从指定的网站中检索这些图标。我们的出版社的图标是从 www.aw-bc.com 网站检索的。而我们第5版（我们最喜欢的封面之一）的封面图像存储在manic.cs.umass.edu服务器。
- 停止Wireshark数据包捕获，并在display-filter-specification窗口中输入“http”，以便只显示捕获的HTTP消息。


回答下列问题：

- 您的浏览器发送了几个HTTP GET请求消息？ 这些GET请求发送到哪个IP地址？

3个http的get请求消息， 发送到 128.119.245。12

- 浏览器从两个网站串行还是并行下载了两张图片？请说明。

并行下载了两张图片， 因为第二个GET请求没有进行握手， 而是直接使用第一次的握手信号。

**5.HTTP认证**

最后，我们尝试访问受密码保护的网站，并检查网站的HTTP消息交换的序列。URL http://gaia.cs.umass.edu/wireshark-labs/protected_pages/HTTP-wireshark-file5.html 是受密码保护的。用户名是“wireshark-students”（不包含引号），密码是“network”（再次不包含引号）。所以让我们访问这个“安全的”受密码保护的网站。

回答下列问题

![image](https://s2.ax1x.com/2020/02/16/3p46S0.png)

- 对于您的浏览器的初始HTTP GET消息，服务器响应（状态码和短语）是什么响应？

401 Unauthorized

- 当您的浏览器第二次发送HTTP GET消息时，HTTP GET消息中包含哪些新字段？

Authorization: Basic d2lyZXNoYXJrLXN0dWRlbnRzOm5ldHdvcms=\r\n

