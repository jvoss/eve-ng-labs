interfaces {
    ethernet eth0 {
        address "172.16.45.5/24"
        address "fd01:101:2d::5/64"
        description "PE4"
    }
    ethernet eth1 {
    }
    ethernet eth2 {
    }
    ethernet eth3 {
    }
    loopback lo {
    }
}
protocols {
    bgp {
        address-family {
            ipv4-unicast
            ipv6-unicast
        }
        neighbor fd01:101:2d::4 {
            address-family {
                ipv4-unicast
                ipv6-unicast
            }
            remote-as "external"
        }
        system-as "65005"
    }
}
service {
    ntp {
        allow-client {
            address "0.0.0.0/0"
            address "::/0"
        }
        server time1.vyos.net {
        }
        server time2.vyos.net {
        }
        server time3.vyos.net {
        }
    }
}
system {
    config-management {
        commit-revisions "100"
    }
    conntrack {
        modules {
            ftp
            h323
            nfs
            pptp
            sip
            sqlnet
            tftp
        }
    }
    console {
        device ttyS0 {
            speed "115200"
        }
    }
    host-name "CE5"
    login {
        user vyos {
            authentication {
                encrypted-password "$6$QxPS.uk6mfo$9QBSo8u1FkH16gMyAVhus6fU3LOzvLR9Z9.82m3tiHFAxTtIkhaZSWssSgzt4v4dGAL8rhVQxTg0oAG9/q11h/"
                plaintext-password ""
            }
        }
    }
    syslog {
        global {
            facility all {
                level "info"
            }
            facility local7 {
                level "debug"
            }
        }
    }
}


// Warning: Do not remove the following line.
// vyos-config-version: "bgp@4:broadcast-relay@1:cluster@2:config-management@1:conntrack@5:conntrack-sync@2:container@1:dhcp-relay@2:dhcp-server@9:dhcpv6-server@4:dns-dynamic@3:dns-forwarding@4:firewall@14:flow-accounting@1:https@6:ids@1:interfaces@32:ipoe-server@3:ipsec@13:isis@3:l2tp@7:lldp@2:mdns@1:monitoring@1:nat@7:nat66@3:ntp@3:openconnect@2:openvpn@1:ospf@2:pim@1:policy@8:pppoe-server@8:pptp@3:qos@2:quagga@11:rip@1:rpki@1:salt@1:snmp@3:ssh@2:sstp@6:system@27:vrf@3:vrrp@4:vyos-accel-ppp@2:wanloadbalance@3:webproxy@2"
// Release version: 1.5-rolling-202401310023